package com.ankitgh.employeeportal.screens.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel @ViewModelInject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStoreDb: FirebaseFirestore
) : ViewModel() {

    private val user = MutableLiveData<Resource<UserSchema>>()

    fun getUser(): LiveData<Resource<UserSchema>> {
        fireStoreDb.collection("users")
            .document(firebaseAuth.currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapShot ->
                val signedInUser = userSnapShot.toObject(UserSchema::class.java)
                if (signedInUser != null) {
                    signedInUser.photoUri = firebaseAuth.currentUser?.photoUrl
                    signedInUser.username = firebaseAuth.currentUser?.displayName.toString()
                }
                user.postValue(Resource.success(signedInUser))
                Log.i("CreatePostViewModel", "Signed in user : $signedInUser")
            }
            .addOnFailureListener { exception ->
                user.postValue(Resource.error(exception.message))
                Log.e("CreatePostViewModel", "Failure fetching Singed-In user : $exception")
            }
        return user
    }
}
