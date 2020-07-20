package com.ankitgh.employeeportal.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ankitgh.employeeportal.ui.home.HomeActivity
import com.ankitgh.employeeportal.ui.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: LauncherViewModel by viewModels()

        viewModel.launchDestination.observe(this, Observer { it ->
            when (it) {
                Destination.ONBOARDING -> startActivity(Intent(this, OnBoardingActivity::class.java))
                Destination.HOME -> startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        })
    }
}