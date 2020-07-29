package com.ankitgh.employeeportal.data.remote.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.ui.feed.FeedPostModel
import com.ankitgh.employeeportal.utils.FirebaseConstants
import com.ankitgh.employeeportal.utils.FirebaseConstants.USER_COLLECTION
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Class to provide data from FirebaseAuth / FirebaseStorage / FirebaseFirestore which have user
 * details for login and fireStore contains list of users and posts while storage contains
 * profile images.
 */
class FirebaseRemoteRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth, private val firebaseStorage: StorageReference,
    private val fireBaseFireStore: FirebaseFirestore
) : FirebaseRemoteDataSource {

    override suspend fun signInUserWithUserNameAndPassword(email: String, password: String): Task<AuthResult> {
        return withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.signInWithEmailAndPassword(email, password)
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun getUser(): MutableLiveData<Resource<UserSchema>> {
        Resource.loading<UserSchema>(isloading = true)
        val result = MutableLiveData<Resource<UserSchema>>()
        fireBaseFireStore.collection(USER_COLLECTION)
            .document(firebaseAuth.currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapShot ->
                val signedInUser = userSnapShot.toObject(UserSchema::class.java)
                if (signedInUser != null) {
                    signedInUser.photoUrl = firebaseAuth.currentUser?.photoUrl
                    signedInUser.username = firebaseAuth.currentUser?.displayName.toString()
                }
                result.postValue(Resource.success(signedInUser))
                Resource.loading<UserSchema>(isloading = false)
                Timber.i("Signed in user : $signedInUser")
            }
            .addOnFailureListener { exception ->
                result.postValue(Resource.error(exception.message))
                Timber.e("Failure fetching Singed-In user : $exception")
                Resource.loading<UserSchema>(isloading = false)
            }
        return result
    }

    override fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>> {
        val userObserver = MutableLiveData<Resource<UserSchema>>()
        val photoReference = firebaseStorage.child("profile_images/${System.currentTimeMillis()}-${userSchema.username}-profile-photo.jpg")

        userObserver.postValue(Resource.loading(true))
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

                fireBaseFireStore.collection(USER_COLLECTION).document(firebaseAuth.currentUser?.uid.toString())
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

    override fun fetchPosts(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>> {
        val postLiveData = MutableLiveData<Resource<PostSchema>>()
        postLiveData.postValue(Resource.loading(isloading = true))
        fireBaseFireStore.collection(FirebaseConstants.POSTS_COLLECTION)
            .orderBy(FirebaseConstants.CREATION_TIME, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null && snapshot == null) {
                    postLiveData.value = Resource.loading(isloading = false)
                    postLiveData.value = Resource.error(exception.message)
                    Timber.e("Exception when retrieving posts from FireStore : ${exception.message}")
                    return@addSnapshotListener
                }
                postList.clear()
                if (snapshot != null) {
                    val postListSnapShot = snapshot.toObjects(PostSchema::class.java)

                    for (post in postListSnapShot) {
                        postList.add(
                            FeedPostModel(
                                profileImage = post.userSchema?.photourl,
                                username = post.userSchema?.username,
                                designation = post.userSchema?.designation,
                                postTime = post.creation_time,
                                feedBody = post.body,
                                likes = post.userSchema?.likes
                            )
                        )
                    }
                    postLiveData.value = Resource.success(null)
                    postLiveData.value = Resource.loading(isloading = false)
                }
            }
        return postLiveData
    }
}