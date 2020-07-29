package com.ankitgh.employeeportal.data

import androidx.lifecycle.LiveData
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.ui.feed.FeedPostModel
import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

/**
 * Interface to data layer
 */
interface MainRepository {

    suspend fun getTopHeadlines(isConnectivityAvailable: Boolean): Resource<List<NewsArticleModel>>

    suspend fun signInUser(email: String, password: String): Task<AuthResult>

    fun isUserAlreadyRegistered(): Boolean

    fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>>

    fun getUser(): LiveData<Resource<UserSchema>>

    fun getCurrentAuthUser(): Flow<FirebaseUser>

    fun fetchPosts(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>>
}
