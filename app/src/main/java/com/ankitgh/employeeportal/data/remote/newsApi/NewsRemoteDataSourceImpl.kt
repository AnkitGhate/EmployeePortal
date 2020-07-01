package com.ankitgh.employeeportal.data.remote.newsApi

import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.Resource
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(private val newsApiService: NewsApiService) : NewsRemoteDataSource {

    companion object {
        private const val API_KEY: String = "ce4eb78d859e428688288488cb9eebb3"
    }

    override suspend fun getTopHeadlines(): Resource<List<NewsArticleModel>> {
        val response = newsApiService.getTopHeadlinesFromGoogle(API_KEY)
        return if (response.isSuccessful) {

            val articlesList = response.body()?.articles
            val newsArticleModelList = ArrayList<NewsArticleModel>()
            articlesList?.forEach {
                newsArticleModelList.add(
                    NewsArticleModel(
                        description = it.description,
                        publishedAt = it.publishedAt,
                        author = it.author,
                        sourceSchema = it.sourceSchema,
                        title = it.title,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        content = it.content

                    )
                )
            }
            Resource.success(newsArticleModelList)
        } else {
            Resource.error(response.errorBody().toString())
        }
    }
}
