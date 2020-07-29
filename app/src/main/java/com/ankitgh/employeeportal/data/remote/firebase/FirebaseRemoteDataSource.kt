package com.ankitgh.employeeportal.data.remote.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.ui.feed.FeedPostModel
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseRemoteDataSource {

    suspend fun signInUserWithUserNameAndPassword(email: String, password: String): Task<AuthResult>

    fun getCurrentUser(): FirebaseUser?

    fun getUser(): MutableLiveData<Resource<UserSchema>>

    fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>>

    fun fetchPosts(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>>
}
