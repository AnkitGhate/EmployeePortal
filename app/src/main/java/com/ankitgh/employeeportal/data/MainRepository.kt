package com.ankitgh.employeeportal.data

import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Interface to data layer
 */
interface MainRepository {

    suspend fun getTopHeadlines(isConnectivityAvailable: Boolean): Resource<List<NewsArticleModel>>

    suspend fun signInUser(email: String, password: String): Task<AuthResult>

}