package com.ankitgh.employeeportal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ankitgh.employeeportal.screens.onboarding.LetsGetStartedFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LetsGetStartedFragment.launchFragment(
            R.id.container,
            fragmentManager = supportFragmentManager
        )
    }
}