package com.ankitgh.employeeportal.data.api

import com.ankitgh.employeeportal.data.model.newsmodel.NewsJsonRootSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    companion object {
        const val ENDPOINT = "https://newsapi.org/v2/"
    }

    @GET("top-headlines?sources=google-news-in")
    suspend fun getTopHeadlinesFromGoogle(@Query("apiKey") apiKey: String? = null): Response<NewsJsonRootSchema>
}
