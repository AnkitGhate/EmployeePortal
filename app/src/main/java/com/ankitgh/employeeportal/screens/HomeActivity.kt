package com.ankitgh.employeeportal.screens

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.BaseActivity
import com.ankitgh.employeeportal.screens.home.HomeFragment
import kotlinx.android.synthetic.main.activity_home.*

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
                R.id.home_tab -> navController.navigate(R.id.action_homeFragment_self)
                R.id.feed_tab -> navController.navigate(R.id.action_homeFragment_to_feedFragment)
                R.id.addressBook_tab -> navController.navigate(R.id.action_feedFragment_to_addressBookFragment)
                R.id.settings_tab -> navController.navigate(R.id.action_addressBookFragment_to_settingsFragment)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }
}