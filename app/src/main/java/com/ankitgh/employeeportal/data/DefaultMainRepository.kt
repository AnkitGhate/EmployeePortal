package com.ankitgh.employeeportal.data

import com.ankitgh.employeeportal.data.remote.firebase.FirebaseRemoteDataSource
import com.ankitgh.employeeportal.data.remote.newsApi.NewsRemoteDataSource
import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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
}
