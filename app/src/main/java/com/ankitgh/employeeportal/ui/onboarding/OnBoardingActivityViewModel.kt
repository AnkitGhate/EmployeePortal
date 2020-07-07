package com.ankitgh.employeeportal.ui.onboarding

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.data.remote.firebase.FirebaseRemoteDataSource
import com.google.firebase.auth.FirebaseUser

class OnBoardingActivityViewModel @ViewModelInject constructor(
    private val firebaseRemoteDataSource: FirebaseRemoteDataSource
) : ViewModel() {

    fun getCurrentUser(): FirebaseUser? {
        return firebaseRemoteDataSource.getCurrentUser()
    }
}
