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

package com.ankitgh.employeeportal.di.module

import com.ankitgh.employeeportal.data.DefaultMainRepository
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.data.remote.firebase.FirebaseRemoteDataSource
import com.ankitgh.employeeportal.data.remote.firebase.FirebaseRemoteRemoteDataSourceImpl
import com.ankitgh.employeeportal.data.remote.newsApi.NewsApiService
import com.ankitgh.employeeportal.data.remote.newsApi.NewsRemoteDataSource
import com.ankitgh.employeeportal.data.remote.newsApi.NewsRemoteDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NewsApiService = retrofit.create(NewsApiService::class.java)

    @Provides
    @Singleton
    fun provideNewsDataSource(newsApiService: NewsApiService): NewsRemoteDataSource = NewsRemoteDataSourceImpl(newsApiService)

    @Provides
    @Singleton
    fun provideFirebaseRemoteDataSource(
        firebaseAuth: FirebaseAuth,
        storageReference: StorageReference,
        firebaseFirestore: FirebaseFirestore
    ): FirebaseRemoteDataSource =
        FirebaseRemoteRemoteDataSourceImpl(firebaseAuth,storageReference,firebaseFirestore)

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        firebaseRemoteDataSourceImpl: FirebaseRemoteDataSource
    ): MainRepository =
        DefaultMainRepository(newsRemoteDataSource, firebaseRemoteDataSourceImpl)
}
