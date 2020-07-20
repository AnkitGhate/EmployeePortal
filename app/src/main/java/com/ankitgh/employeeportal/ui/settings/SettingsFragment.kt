package com.ankitgh.employeeportal.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
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
        signout_textview.setOnClickListener {
            firebaseAuth.signOut()
            settingsNavController.navigate(R.id.onboarding_activity)
        }
    }
}
