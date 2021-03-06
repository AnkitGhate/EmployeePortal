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

package com.ankitgh.employeeportal.data.remote.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ankitgh.employeeportal.data.model.firestoremodel.PostSchema
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.ui.feed.FeedPostModel
import com.ankitgh.employeeportal.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseRemoteDataSource {

    suspend fun signInUserWithUserNameAndPassword(email: String, password: String): Task<AuthResult>

    fun getCurrentUser(): FirebaseUser?

    fun getUser(): MutableLiveData<Resource<UserSchema>>

    fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>>

    fun fetchPosts(postList: ArrayList<FeedPostModel>): LiveData<Resource<PostSchema>>
}
