package com.ankitgh.employeeportal.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.ui.onboarding.OnBoardingActivity
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.settings_fragment.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var settingsNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val forward = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        enterTransition = forward

        val backward = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = backward
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

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

        signout_textview.setOnClickListener {
            firebaseAuth.signOut()
            //TODO : Fix crash on navigation
            val intent = Intent(activity, OnBoardingActivity::class.java)
            startActivity(intent)
            //settingsNavController.navigate(R.id.onboarding_activity)
        }
    }
}
