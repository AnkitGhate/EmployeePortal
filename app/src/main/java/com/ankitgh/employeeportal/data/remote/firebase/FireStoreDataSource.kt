package com.ankitgh.employeeportal.data.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface FireStoreDataSource {

    suspend fun signInUserWithUserNameAndPassword(email: String, password: String): Task<AuthResult>

}