package com.ankitgh.employeeportal.data.remote.newsApi

import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.Resource

interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(): Resource<List<NewsArticleModel>>

}