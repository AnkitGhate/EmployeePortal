package com.ankitgh.employeeportal.data

import com.ankitgh.employeeportal.data.remote.firebase.FireStoreDataSource
import com.ankitgh.employeeportal.data.remote.newsApi.NewsRemoteDataSource

/**
 * Main entry point for accessing News Data
 */
interface MainDataSource : NewsRemoteDataSource, FireStoreDataSource