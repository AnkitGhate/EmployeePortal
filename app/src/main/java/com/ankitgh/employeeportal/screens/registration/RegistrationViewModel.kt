package com.ankitgh.employeeportal.screens.registration

import android.net.Uri
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.data.firestoremodel.User
import com.ankitgh.employeeportal.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference


class RegistrationViewModel @ViewModelInject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorageRef: StorageReference
) : ViewModel() {

    private val userObserver = MutableLiveData<Resource<User>>()

    fun registerUser(user: User): LiveData<Resource<User>> {
        val photoReference = firebaseStorageRef.child("profile_images/${System.currentTimeMillis()}-${user.username}-profile-photo.jpg")
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .continueWithTask { userRegistrationTask ->
                //Upload profile image to firebase storage
                user.photoUri?.let { photoReference.putFile(it) }
            }.continueWithTask { imageUploadTask ->
                //Download url of the image uploaded
                if(imageUploadTask.isSuccessful){
                    Log.d("TTTT","imageUploadTask : ${imageUploadTask.result.toString()}")
                }
                photoReference.downloadUrl
            }.continueWithTask { downloadUrlTask ->
                val imageuri : Uri = Uri.parse(downloadUrlTask.result.toString())
                Log.d("TTTT", "downloadUrlTask Path : ${imageuri}")
                //update the new user in firebase auth with the profile url and username
                val updateRequest: UserProfileChangeRequest = userProfileChangeRequest {
                    displayName = user.username
                    photoUri = imageuri
                    build()
                }
                firebaseAuth.currentUser?.updateProfile(updateRequest)
            }.continueWithTask { userUpdateTask ->
                if (userUpdateTask.isSuccessful) {
                    Log.d("TTTT", "User updated with username and profile image path")
                }
                //Add user to firestore
                val userData: MutableMap<String, Any> = HashMap()
                userData["username"] = user.username
                userData["designation"] = user.designation

                firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
                    .set(userData)
                    .addOnSuccessListener {
                        Log.d("TTTT", "document added successfully ")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("TTTT", "Error adding document", exception)
                    }
            }.addOnCompleteListener { updateFireStoreTask ->
                if (updateFireStoreTask.isSuccessful) {
                    userObserver.postValue(Resource.success(User(isSignUpComplete = true)))
                    Log.i("TTTT", "All task for user registration are complete!")
                } else {
                    userObserver.postValue(Resource.error(updateFireStoreTask.exception.toString()))
                    Log.e(
                        "TTTT", "Error while user registration : Exception :" +
                                " ${updateFireStoreTask.exception?.stackTrace}"
                    )
                }
            }
        return userObserver
    }
}