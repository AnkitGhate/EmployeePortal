package com.ankitgh.employeeportal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import kotlinx.android.synthetic.main.activity_home.*

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        navController = Navigation.findNavController(this, setNavControllerId())

    }

    fun navigateTo(destination: Int) {
        navController.navigate(destination)
    }

    fun setUpBottomNavigation() {
        bottom_chip_navigation_bar.setOnItemSelectedListener {
            when (it) {
                R.id.home_tab -> {
                    navController.popBackStack(R.id.homeFragment, false)
                    navigateTo(R.id.homeFragment)
                }
                R.id.feed_tab -> {
                    navController.popBackStack(R.id.homeFragment, false)
                    navigateTo(R.id.feedFragment)
                }
                R.id.addressBook_tab -> {
                    navController.popBackStack(R.id.homeFragment, false)
                    navigateTo(R.id.addressBookFragment)
                }
                R.id.settings_tab -> {
                    navController.popBackStack(R.id.homeFragment, false)
                    navigateTo(R.id.settingsFragment)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //Hack for bottom nav bar to be selected on back press
        if (navController.currentDestination?.label == "home_fragment") {
            bottom_chip_navigation_bar.setItemSelected(R.id.home_tab)
        }
    }

    abstract fun setLayoutId(): Int

    abstract fun setNavControllerId(): Int
}
