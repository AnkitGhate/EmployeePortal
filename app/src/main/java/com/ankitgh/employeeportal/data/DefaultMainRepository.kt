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

package com.ankitgh.employeeportal.data

import androidx.lifecycle.LiveData
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.data.remote.firebase.FirebaseRemoteDataSource
import com.ankitgh.employeeportal.data.remote.newsApi.NewsRemoteDataSource
import com.ankitgh.employeeportal.ui.article.ArticleModel
import com.ankitgh.employeeportal.ui.article.articleDetail.ArticleDetail
import com.ankitgh.employeeportal.ui.feed.FeedPostModel
import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.BlogConstants
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jsoup.Jsoup
import timber.log.Timber
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val mainRemoteDataSource: NewsRemoteDataSource,
    private val firebaseRemoteDataSource: FirebaseRemoteDataSource
) : MainRepository {

    override suspend fun fetchTopNewsHeadlines(isConnectivityAvailable: Boolean): Resource<List<NewsArticleModel>> {
        return mainRemoteDataSource.getTopHeadlines()
    }

    override suspend fun signIn(email: String, password: String): Task<AuthResult> {
        return firebaseRemoteDataSource.signInUserWithUserNameAndPassword(email, password)
    }

    override fun isUserAlreadyRegistered(): Boolean {
        return firebaseRemoteDataSource.getCurrentUser() != null
    }

    override fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>> {
        return firebaseRemoteDataSource.registerUser(userSchema)
    }

    override fun getUser(): LiveData<Resource<UserSchema>> {
        return firebaseRemoteDataSource.getUser()
    }

    override fun getCurrentAuthUser() = flow {
        firebaseRemoteDataSource.getCurrentUser()?.let { emit(it) }
    }

    override fun fetchPosts(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>> {
        return firebaseRemoteDataSource.fetchPosts(postList)
    }

    override fun fetchBlogPosts() = flow {
        emit(Resource.loading(isloading = true))
        val listOfArticles: MutableList<ArticleModel> = mutableListOf()

        try {
            val articles = Jsoup.connect(BlogConstants.BASE_URL).get()

            val articleMetaData = articles.getElementsByClass(BlogConstants.POST_COL_MD_3)

            for (blogPosts in articleMetaData) {
                val root = blogPosts.getElementsByClass("post col-md-3")

                val title = root.first().getElementsByClass("article").first()
                    .getElementsByClass("title").first()
                    .select("a[href]").text()

                val metaDesc = root.first().getElementsByClass("post-description").text()

                val date = root.first().getElementsByClass("article").first()
                    .getElementsByClass("entry-date pull-left")[0].select("span").text()

                val articleImage = root.first().getElementsByClass("post col-md-3").first()
                    .getElementsByClass("row post-image-container").select("a[href]").select("img").attr("src")

                val readingTime = root.first().getElementsByClass("article").first()
                    .getElementsByClass("entry-date pull-left")[1].select("span").text()

                val articleURL = root.first().getElementsByClass("read-more").select("a").first().select("a[href]").attr("href")

                listOfArticles.add(
                    ArticleModel(
                        0,
                        articleTitle = title,
                        articleAuthor = "Arun Nathani",
                        articleImageUrl = articleImage,
                        articleMetaDescription = metaDesc,
                        readingTime = readingTime,
                        articleURL = articleURL
                    )
                )
            }
            emit(Resource.loading(isloading = false))
            emit(Resource.success(data = listOfArticles))
        } catch (exception: Throwable) {
            emit(Resource.loading(isloading = false))
            emit(Resource.error(exception.message))
            Timber.e(exception)
        }
    }.flowOn(Dispatchers.IO)


    override fun fetchArticleDetails(articleURL: String) = flow {
        emit(Resource.loading(true))
        val root = Jsoup.connect(articleURL).get()
        val body = root.getElementsByClass("post-detail").first().getElementById("wpa_content").text()
        val readingTime = root.getElementsByClass("post-detail").first().getElementsByClass("entry-standard entry-length col-xs-6 col-sm-3").select("span").text()

        emit(Resource.loading(isloading = false))
        emit(Resource.success(ArticleDetail(readingTime = readingTime, body = body, author = "Arun Nathani")))
    }.flowOn(Dispatchers.IO)
}
