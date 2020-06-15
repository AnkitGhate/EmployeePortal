package com.ankitgh.employeeportal.data.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<String>>
}