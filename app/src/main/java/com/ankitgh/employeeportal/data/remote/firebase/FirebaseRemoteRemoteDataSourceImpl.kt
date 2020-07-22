package com.ankitgh.employeeportal.data.remote.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.utils.Resource
import com.ankitgh.employeeportal.utils.Status
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class FirebaseRemoteRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: StorageReference,
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseRemoteDataSource {

    override suspend fun signInUserWithUserNameAndPassword(email: String, password: String): Task<AuthResult> {
        return withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.signInWithEmailAndPassword(email, password)
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    /**
     * Get user information mapped to current signed in userinfo from FirebaseAuth and as FirebaseFirestore.collect already
     * run on a different thread so no need to run on different thread.
     */
    override fun getUser(): MutableLiveData<Resource<UserSchema>> {
        val result = MutableLiveData<Resource<UserSchema>>()
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapShot ->
                val signedInUser = userSnapShot.toObject(UserSchema::class.java)
                if (signedInUser != null) {
                    signedInUser.photoUrl = firebaseAuth.currentUser?.photoUrl
                    signedInUser.username = firebaseAuth.currentUser?.displayName.toString()
                }
                result.postValue(Resource.success(signedInUser))
                Timber.i("Signed in user : $signedInUser")
            }
            .addOnFailureListener { exception ->
                result.postValue(Resource.error(exception.message))
                Timber.e("Failure fetching Singed-In user : $exception")
            }
        return result
    }


    override fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>> {
        val userObserver = MutableLiveData<Resource<UserSchema>>()

        val photoReference = firebaseStorage.child("profile_images/${System.currentTimeMillis()}-${userSchema.username}-profile-photo.jpg")

        firebaseAuth.createUserWithEmailAndPassword(userSchema.email, userSchema.password)
            .continueWithTask { userRegistrationTask ->
                // Upload profile image to firebase storage
                userRegistrationTask.isSuccessful
                userSchema.photoUrl?.let { photoReference.putFile(it) }
            }.continueWithTask {
                // Download url of the image uploaded
                photoReference.downloadUrl
            }.continueWithTask { downloadUrlTask ->
                // update the new user in firebase auth with the profile url and username
                val updateRequest: UserProfileChangeRequest = userProfileChangeRequest {
                    displayName = userSchema.username
                    photoUri = downloadUrlTask.result
                    build()
                }
                firebaseAuth.currentUser?.updateProfile(updateRequest)
            }.continueWithTask {
                // Add user to firestore
                val userData: MutableMap<String, Any> = HashMap()
                userData["username"] = userSchema.username
                userData["designation"] = userSchema.designation

                firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
                    .set(userData)
                    .addOnSuccessListener {
                        Timber.d("document added successfully")
                    }
                    .addOnFailureListener { exception ->
                        Timber.e("Error adding document: $exception")
                    }
            }.addOnCompleteListener { updateFireStoreTask ->
                if (updateFireStoreTask.isSuccessful) {
                    userObserver.postValue(Resource.success(UserSchema(isSignUpComplete = true)))
                    Timber.d("All task for user registration are complete!")
                } else {
                    userObserver.postValue(Resource.error(updateFireStoreTask.exception.toString()))
                    Timber.e("Error while user registration : Exception : ${updateFireStoreTask.exception?.stackTrace}")
                }
            }
        return userObserver
    }
}
