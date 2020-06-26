package com.ankitgh.employeeportal.data

import com.ankitgh.employeeportal.common.Resource
import com.ankitgh.employeeportal.data.remote.newsApi.NewsApiService
import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class MainRemoteDataSource @Inject constructor(private val newsApiService: NewsApiService) : MainDataSource {

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

    override suspend fun signInUserWithUserNameAndPassword(email: String, password: String): Task<AuthResult> {
        TODO("Not yet implemented")
    }
}
