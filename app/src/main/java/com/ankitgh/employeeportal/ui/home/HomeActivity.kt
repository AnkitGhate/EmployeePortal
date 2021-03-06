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

package com.ankitgh.employeeportal.ui.home

import android.os.Bundle
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.ui.BaseActivity
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
