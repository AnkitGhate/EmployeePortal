package com.ankitgh.employeeportal.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.BaseActivity
import com.ankitgh.employeeportal.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeFragment.launchFragment(
            R.id.home_nav_host_fragment,
            fragmentManager = supportFragmentManager
        )
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        navController = Navigation.findNavController(this, R.id.home_nav_host_fragment)

        bottom_chip_navigation_bar.setOnItemSelectedListener {
            when (it) {
                R.id.home_tab -> navController.navigate(R.id.homeFragment)
                R.id.feed_tab -> navController.navigate(R.id.feedFragment)
                R.id.addressBook_tab -> navController.navigate(R.id.addressBookFragment)
                R.id.settings_tab -> navController.navigate(R.id.settingsFragment)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }
}
