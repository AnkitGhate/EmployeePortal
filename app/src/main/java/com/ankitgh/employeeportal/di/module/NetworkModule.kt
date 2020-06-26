package com.ankitgh.employeeportal.di.module

import com.ankitgh.employeeportal.data.DefaultMainRepository
import com.ankitgh.employeeportal.data.MainDataSource
import com.ankitgh.employeeportal.data.MainRemoteDataSource
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.data.remote.firebase.FirebaseRemoteDataSource
import com.ankitgh.employeeportal.data.remote.newsApi.NewsApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteTasksDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class FirebaseDataSource

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NewsApiService = retrofit.create(NewsApiService::class.java)

    @Provides
    @Singleton
    @RemoteTasksDataSource
    fun provideMainDataSource(newsApiService: NewsApiService): MainDataSource = MainRemoteDataSource(newsApiService)

    @Provides
    @Singleton
    @FirebaseDataSource
    fun provideFirebaseDataSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): MainDataSource = FirebaseRemoteDataSource(firebaseAuth, firebaseFirestore, firebaseStorage)

    @Provides
    @Singleton
    fun provideNewsRepository(mainDataSource: MainDataSource, firebaseRemoteDataSource: FirebaseRemoteDataSource): MainRepository =
        DefaultMainRepository(mainDataSource, firebaseRemoteDataSource)
}