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

package com.ankitgh.employeeportal.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.ui.onboarding.OnBoardingActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.settings_fragment.*

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.settings_fragment) {

    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var settingsNavController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = Firebase.auth
        settingsNavController = Navigation.findNavController(view)

        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.photoUrl)
                .placeholder(R.drawable.ic_default_profile_avatar)
                .into(profile_image)
        })

        settings_logout_button.setOnClickListener {
            firebaseAuth.signOut()
            //TODO : Fix crash on navigation
            val intent = Intent(activity, OnBoardingActivity::class.java)
            startActivity(intent)
            //settingsNavController.navigate(R.id.onboarding_activity)
        }
    }
}
