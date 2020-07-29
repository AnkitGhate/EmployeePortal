/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ankitgh.employeeportal.data.remote.newsApi

import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.Resource
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(private val newsApiService: NewsApiService) : NewsRemoteDataSource {

    companion object {
        private const val API_KEY: String = "ce4eb78d859e428688288488cb9eebb3"
    }

    override suspend fun getTopHeadlines(): Resource<List<NewsArticleModel>> {
        Resource.loading<List<NewsArticleModel>>(isloading = true)
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
            Resource.loading<List<NewsArticleModel>>(isloading = false)
            Resource.success(newsArticleModelList)
        } else {
            Resource.loading<List<NewsArticleModel>>(isloading = false)
            Resource.error(response.errorBody().toString())
        }
    }
}
