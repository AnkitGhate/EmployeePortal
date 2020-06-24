package com.ankitgh.employeeportal.data.api

import com.ankitgh.employeeportal.common.Resource
import com.ankitgh.employeeportal.ui.home.NewsArticleModel

class NewsNewsApiHelperImpl constructor(private val newsApiService: NewsApiService) : NewsApiHelper {

    companion object {
        private const val API_KEY: String = "ce4eb78d859e428688288488cb9eebb3"
    }

    override suspend fun getTopHeadlines(): Resource<List<NewsArticleModel>> {
        val response = newsApiService.getTopHeadlinesFromGoogle(API_KEY)
        return if (response.isSuccessful) {
            val articlesList = response.body()?.articles
            val newsArticleModelList = ArrayList<NewsArticleModel>()
            articlesList?.forEach {
                newsArticleModelList.add(NewsArticleModel(it.title, it.publishedAt))
            }
            Resource.success(newsArticleModelList)
        } else {
            Resource.error(response.errorBody().toString())
        }
    }
}
