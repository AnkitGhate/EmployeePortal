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
import com.ankitgh.employeeportal.ui.feed.FeedPostModel
import com.ankitgh.employeeportal.ui.home.NewsArticleModel
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val mainRemoteDataSource: NewsRemoteDataSource,
    private val firebaseRemoteDataSource: FirebaseRemoteDataSource
) : MainRepository {

    override suspend fun getTopHeadlines(isConnectivityAvailable: Boolean): Resource<List<NewsArticleModel>> {
        return if (isConnectivityAvailable) {
            mainRemoteDataSource.getTopHeadlines()
        } else {
            TODO()
            // newsLocalDataSource.getTopHeadlines()
        }
    }

    override suspend fun signInUser(email: String, password: String): Task<AuthResult> {
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
}
