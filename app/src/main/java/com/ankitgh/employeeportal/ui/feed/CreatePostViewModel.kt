package com.ankitgh.employeeportal.ui.feed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.data.model.firestoremodel.BasicUserInfoSchema
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class CreatePostViewModel @ViewModelInject constructor(
    private val firebaseDb: FirebaseFirestore, private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var user = MutableLiveData<Resource<UserSchema>>()

    fun sendPostToFirebaseDB(postBody: String): LiveData<Resource<UserSchema>> {
        user.postValue(Resource.loading())

        firebaseDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapShot ->
                val signedInUser = userSnapShot.toObject(BasicUserInfoSchema::class.java)
                Timber.i("Signed in user : $signedInUser")
                val post = PostSchema(
                    postBody, System.currentTimeMillis(), BasicUserInfoSchema(
                        username = signedInUser?.username,
                        designation = signedInUser?.designation,
                        email = firebaseAuth.currentUser?.email.toString(),
                        photourl = firebaseAuth.currentUser?.photoUrl.toString()
                    )
                )
                firebaseDb.collection("posts").add(post)
                    .addOnSuccessListener {
                        user.postValue(Resource.success(null))
                        Timber.i("Post submitted to firebase")
                    }
                    .addOnFailureListener { exception ->
                        user.postValue(Resource.error(exception.message))
                        Timber.e("Failed to send post to firebase : $exception")
                    }
            }
            .addOnFailureListener { exception ->
                user.postValue(Resource.error(exception.message))
                Timber.e("Failure fetching Singed-In user : $exception")
            }
        return user
    }
}
