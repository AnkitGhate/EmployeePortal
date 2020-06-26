package com.ankitgh.employeeportal.data.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FireStoreDataSource {

    override suspend fun signInUserWithUserNameAndPassword(email: String, password: String): Task<AuthResult> {
        return withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.signInWithEmailAndPassword(email, password)
        }
    }
}