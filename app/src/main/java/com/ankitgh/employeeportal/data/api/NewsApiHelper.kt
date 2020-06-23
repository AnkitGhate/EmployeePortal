package com.ankitgh.employeeportal.data.api

import com.ankitgh.employeeportal.data.model.newsmodel.NewsJsonRootSchema
import retrofit2.Response

interface NewsApiHelper {
    suspend fun getTopHeadlines(): Response<NewsJsonRootSchema>
}