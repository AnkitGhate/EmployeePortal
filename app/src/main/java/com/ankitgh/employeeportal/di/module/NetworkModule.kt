package com.ankitgh.employeeportal.di.module

import com.ankitgh.employeeportal.data.DefaultMainRepository
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.data.remote.firebase.FirebaseRemoteDataSource
import com.ankitgh.employeeportal.data.remote.firebase.FirebaseRemoteRemoteDataSourceImpl
import com.ankitgh.employeeportal.data.remote.newsApi.NewsApiService
import com.ankitgh.employeeportal.data.remote.newsApi.NewsRemoteDataSource
import com.ankitgh.employeeportal.data.remote.newsApi.NewsRemoteDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
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
    fun provideFirebaseRemoteDataSource(firebaseAuth: FirebaseAuth): FirebaseRemoteDataSource = FirebaseRemoteRemoteDataSourceImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        firebaseRemoteDataSourceImpl: FirebaseRemoteDataSource
    ): MainRepository =
        DefaultMainRepository(newsRemoteDataSource, firebaseRemoteDataSourceImpl)
}
