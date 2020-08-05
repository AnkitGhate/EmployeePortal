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
import com.ankitgh.employeeportal.ui.article.ArticleModel
import com.ankitgh.employeeportal.ui.article.articleDetail.ArticleDetail
import com.ankitgh.employeeportal.ui.feed.FeedPostModel
import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

/**
 * Interface to data layer
 */
interface MainRepository {

    suspend fun fetchTopNewsHeadlines(isConnectivityAvailable: Boolean): Resource<List<NewsArticleModel>>

    suspend fun signIn(email: String, password: String): Task<AuthResult>

    fun isUserAlreadyRegistered(): Boolean

    fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>>

    fun getUser(): LiveData<Resource<UserSchema>>

    fun getCurrentAuthUser(): Flow<FirebaseUser>

    fun fetchPosts(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>>

    fun fetchBlogPosts(): Flow<Resource<MutableList<ArticleModel>>>

    fun fetchArticleDetails(articleURL: String): Flow<Resource<ArticleDetail>>
}
