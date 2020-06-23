package com.ankitgh.employeeportal.data.api

import com.ankitgh.employeeportal.data.model.newsmodel.NewsJsonRootSchema
import retrofit2.Response
import javax.inject.Inject

class NewsNewsApiHelperImpl @Inject constructor(private val newsApiService: NewsApiService) : NewsApiHelper {

    companion object {
        private const val API_KEY: String = "ce4eb78d859e428688288488cb9eebb3"
    }

    override suspend fun getTopHeadlines(): Response<NewsJsonRootSchema> = newsApiService.getTopHeadlinesFromGoogle(API_KEY)


}