package com.ankitgh.employeeportal.data.api

import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers(): Response<List<String>>
}