package com.ankitgh.employeeportal.common.di.module

import com.ankitgh.employeeportal.data.api.NewsApiHelper
import com.ankitgh.employeeportal.data.api.NewsApiService
import com.ankitgh.employeeportal.data.api.NewsNewsApiHelperImpl
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
    fun provideNewsApiHelper(newsApiService: NewsApiService): NewsApiHelper = NewsNewsApiHelperImpl(newsApiService)
}