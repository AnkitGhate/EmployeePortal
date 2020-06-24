package com.ankitgh.employeeportal.ui.registration

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.common.Resource
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class RegistrationViewModel @ViewModelInject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val fireBaseStorage = FirebaseStorage.getInstance().reference
    private val userObserver = MutableLiveData<Resource<UserSchema>>()

    fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>> {
        val photoReference = fireBaseStorage.child("profile_images/${System.currentTimeMillis()}-${userSchema.username}-profile-photo.jpg")

        firebaseAuth.createUserWithEmailAndPassword(userSchema.email, userSchema.password)
            .continueWithTask { userRegistrationTask ->
                // Upload profile image to firebase storage
                userRegistrationTask.isSuccessful

                userSchema.photoUri?.let { photoReference.putFile(it) }
            }.continueWithTask { imageUploadTask ->
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
            }.continueWithTask { userUpdateTask ->
                // Add user to firestore
                val userData: MutableMap<String, Any> = HashMap()
                userData["username"] = userSchema.username
                userData["designation"] = userSchema.designation

                firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
                    .set(userData)
                    .addOnSuccessListener {
                        Log.d("RegistrationModel", "document added successfully")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("RegistrationModel", "Error adding document", exception)
                    }
            }.addOnCompleteListener { updateFireStoreTask ->
                if (updateFireStoreTask.isSuccessful) {
                    userObserver.postValue(Resource.success(UserSchema(isSignUpComplete = true)))
                    Log.i("RegistrationViewModel", "All task for user registration are complete!")
                } else {
                    userObserver.postValue(Resource.error(updateFireStoreTask.exception.toString()))
                    Log.e("RegistrationViewModel", "Error while user registration : Exception : ${updateFireStoreTask.exception?.stackTrace}")
                }
            }
        return userObserver
    }
}
