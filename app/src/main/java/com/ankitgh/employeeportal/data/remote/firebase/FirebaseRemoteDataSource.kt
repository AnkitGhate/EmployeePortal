package com.ankitgh.employeeportal.data.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseRemoteDataSource {

    suspend fun signInUserWithUserNameAndPassword(email: String, password: String): Task<AuthResult>

    fun getCurrentUser(): FirebaseUser?
}
