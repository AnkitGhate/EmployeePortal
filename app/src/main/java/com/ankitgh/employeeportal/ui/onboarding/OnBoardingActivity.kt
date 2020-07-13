package com.ankitgh.employeeportal.ui.onboarding

import android.os.Bundle
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateTo(R.id.letsGetStartedFragment)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_onboarding
    }

    override fun setNavControllerId(): Int {
        return R.id.onboarding_nav_host_fragment
    }
}
