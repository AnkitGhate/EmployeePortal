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

import com.ankitgh.employeeportal.BuildConfig
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.data.remote.newsApi.NewsApiService
import com.ankitgh.employeeportal.domain.FetchPostsUserCase
import com.ankitgh.employeeportal.domain.GetTopHeadlinesUseCase
import com.ankitgh.employeeportal.domain.GetUserInfoUserCase
import com.ankitgh.employeeportal.ui.home.newsdetail.ContainerTransformConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = NewsApiService.ENDPOINT

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun getTopHeadlinesUseCase(mainRepository: MainRepository): GetTopHeadlinesUseCase = GetTopHeadlinesUseCase(mainRepository)

    @Provides
    @Singleton
    fun getUserInfoUseCase(mainRepository: MainRepository): GetUserInfoUserCase = GetUserInfoUserCase(mainRepository)

    @Provides
    @Singleton
    fun getPostsUseCase(mainRepository: MainRepository): FetchPostsUserCase = FetchPostsUserCase(mainRepository)

    @Provides
    fun provideContainerTransformConfiguration(): ContainerTransformConfiguration? {
        return ContainerTransformConfiguration()
    }
}
