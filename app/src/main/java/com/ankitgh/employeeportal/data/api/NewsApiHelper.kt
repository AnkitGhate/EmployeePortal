package com.ankitgh.employeeportal.data.api

import com.ankitgh.employeeportal.common.Resource
import com.ankitgh.employeeportal.ui.home.NewsArticleModel

interface NewsApiHelper {
    suspend fun getTopHeadlines(): Resource<List<NewsArticleModel>>
}
