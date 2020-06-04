package com.ankitgh.employeeportal.screens.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var onBoardingNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        firebaseAuth = Firebase.auth
        onBoardingNavController =
            Navigation.findNavController(this, R.id.onboarding_nav_host_fragment)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            onBoardingNavController.navigate(R.id.homeActivity)
        } else {
            LetsGetStartedFragment.launchFragment(
                R.id.onboarding_nav_host_fragment,
                fragmentManager = supportFragmentManager
            )
        }
    }
}