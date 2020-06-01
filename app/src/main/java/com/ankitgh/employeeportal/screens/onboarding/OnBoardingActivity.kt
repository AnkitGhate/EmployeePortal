package com.ankitgh.employeeportal.screens.onboarding

import android.os.Bundle
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.BaseActivity

class OnBoardingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LetsGetStartedFragment.launchFragment(
            R.id.onboarding_nav_host_fragment,
            fragmentManager = supportFragmentManager
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_onboarding
    }
}