package com.ankitgh.employeeportal.data.repository

import com.ankitgh.employeeportal.common.Resource
import com.ankitgh.employeeportal.common.Status
import com.ankitgh.employeeportal.data.api.NewsApiHelper
import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainRepository @Inject constructor(private val newsApiHelper: NewsApiHelper) {

    fun getTopHeadlines(isConnectivityAvailable: Boolean, callback: (Resource<List<NewsArticleModel>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            if (isConnectivityAvailable) {
                val responseData = newsApiHelper.getTopHeadlines()
                when (responseData.status) {
                    Status.SUCCESS -> callback(Resource.success(responseData.data))
                    Status.ERROR -> callback(Resource.error(responseData.message))
                    Status.LOADING -> callback(Resource.loading())
                }
            } else {
                callback(Resource.error("Seems like you are not connected to Network. Please check your network connection"))
            }
        }
    }
}
