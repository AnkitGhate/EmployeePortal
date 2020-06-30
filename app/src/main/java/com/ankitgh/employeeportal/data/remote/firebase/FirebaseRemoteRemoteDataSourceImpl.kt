package com.ankitgh.employeeportal.data.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRemoteRemoteDataSourceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : FirebaseRemoteDataSource {

    override suspend fun signInUserWithUserNameAndPassword(email: String, password: String): Task<AuthResult> {
        return withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.signInWithEmailAndPassword(email, password)
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}