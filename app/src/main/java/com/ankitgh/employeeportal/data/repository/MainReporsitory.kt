package com.ankitgh.employeeportal.data.repository

import com.ankitgh.employeeportal.data.api.ApiHelper
import javax.inject.Inject

class MainReporsitory @Inject constructor(val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}