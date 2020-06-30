package com.ankitgh.employeeportal.domain

import com.ankitgh.employeeportal.data.MainRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val mainRepository: MainRepository) {

    suspend fun signInUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return mainRepository.signInUser(email, password)
    }
}