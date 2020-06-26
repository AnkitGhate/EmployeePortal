package com.ankitgh.employeeportal.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var onBoardingNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        firebaseAuth = Firebase.auth
        onBoardingNavController = Navigation.findNavController(this, R.id.onboarding_nav_host_fragment)

        val currentUser = firebaseAuth.currentUser
        when {
            currentUser != null -> {
                onBoardingNavController.navigate(R.id.homeActivity)
                finish()
            }
            else -> {
                onBoardingNavController.navigate(R.id.letsGetStartedFragment)
            }
        }
    }
}
