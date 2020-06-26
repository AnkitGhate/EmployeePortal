package com.ankitgh.employeeportal.data.remote.newsApi

import com.ankitgh.employeeportal.common.Resource
import com.ankitgh.employeeportal.ui.home.NewsArticleModel

interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(): Resource<List<NewsArticleModel>>

}