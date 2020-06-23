package com.ankitgh.employeeportal.data.repository

import com.ankitgh.employeeportal.data.api.NewsApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val newsApiHelper: NewsApiHelper) {

    suspend fun getTopHeadlines() = newsApiHelper.getTopHeadlines()
}