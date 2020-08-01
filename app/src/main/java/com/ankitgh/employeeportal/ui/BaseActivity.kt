/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
                R.id.article_tab -> {
                    navController.popBackStack(R.id.articleFragment, false)
                    navigateTo(R.id.articleFragment)
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
        // Hack for bottom nav bar to be selected on back press
        if (navController.currentDestination?.label == "home_fragment") {
            bottom_chip_navigation_bar.setItemSelected(R.id.home_tab)
        }
    }


    abstract fun setLayoutId(): Int

    abstract fun setNavControllerId(): Int
}
