/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        user.postValue(Resource.loading(isloading = true))

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
