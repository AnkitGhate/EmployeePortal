package com.ankitgh.employeeportal.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.data.remote.firebase.FirebaseRemoteDataSource
import com.ankitgh.employeeportal.data.remote.newsApi.NewsRemoteDataSource
import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val mainRemoteDataSource: NewsRemoteDataSource,
    private val firebaseRemoteDataSource: FirebaseRemoteDataSource
) : MainRepository {

    override suspend fun getTopHeadlines(isConnectivityAvailable: Boolean): Resource<List<NewsArticleModel>> {
        return if (isConnectivityAvailable) {
            mainRemoteDataSource.getTopHeadlines()
        } else {
            TODO()
            // newsLocalDataSource.getTopHeadlines()
        }
    }

    override suspend fun signInUser(email: String, password: String): Task<AuthResult> {
        return firebaseRemoteDataSource.signInUserWithUserNameAndPassword(email, password)
    }

    override fun isUserAlreadyRegistered(): Boolean {
        return firebaseRemoteDataSource.getCurrentUser() != null
    }

    override fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>> {
        return firebaseRemoteDataSource.registerUser(userSchema)
    }

    override fun getUser(): LiveData<Resource<UserSchema>> {
        return firebaseRemoteDataSource.getUser()
    }

    override fun getCurrentAuthUser() = flow<FirebaseUser> {
        firebaseRemoteDataSource.getCurrentUser()?.let { emit(it) }
    }
}
