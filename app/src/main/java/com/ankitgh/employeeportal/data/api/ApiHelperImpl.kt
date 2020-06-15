package com.ankitgh.employeeportal.data.api

import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<String>> = apiService.getUsers()

}