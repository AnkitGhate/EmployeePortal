package com.ankitgh.employeeportal.ui.feed

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.common.Resource
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreatePostViewModel @ViewModelInject constructor(
    private val firebaseDb: FirebaseFirestore
) : ViewModel() {

    var user = MutableLiveData<Resource<UserSchema>>()

    fun sendPostToFirebaseDB(postBody: String): LiveData<Resource<UserSchema>> {
        user.postValue(Resource.loading())

        firebaseDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapShot ->
                val signedInUser = userSnapShot.toObject(UserSchema::class.java)
                Log.i("CreatePostViewModel", "Signed in user : $signedInUser")

                val post = PostSchema(postBody, System.currentTimeMillis(), signedInUser)
                firebaseDb.collection("posts").add(post)
                    .addOnSuccessListener {
                        user.postValue(Resource.success(null))
                        Log.i("CreatePostViewModel", "Post submitted to firebase")
                    }
                    .addOnFailureListener { exception ->
                        user.postValue(Resource.error(exception.message))
                        Log.e("CreatePostViewModel", "Failed to send post to firebase : $exception")
                    }
            }
            .addOnFailureListener { exception ->
                user.postValue(Resource.error(exception.message))
                Log.e("CreatePostViewModel", "Failure fetching Singed-In user : $exception")
            }
        return user
    }
}
