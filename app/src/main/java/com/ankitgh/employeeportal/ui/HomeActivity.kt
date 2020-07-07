package com.ankitgh.employeeportal.ui

import android.os.Bundle
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeFragment.launchFragment(
            R.id.home_nav_host_fragment,
            fragmentManager = supportFragmentManager
        )
        setUpBottomNavigation()
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun setNavControllerId(): Int {
        return R.id.home_nav_host_fragment
    }
}
