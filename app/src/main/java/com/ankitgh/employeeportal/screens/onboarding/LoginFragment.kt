package com.ankitgh.employeeportal.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.ankitgh.employeeportal.R

class LoginFragment : Fragment() {

    companion object {
        @JvmStatic
        fun launchFragment(containerView: Int, fragmentManager: FragmentManager) {
            val loginFragment = LoginFragment()
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(containerView, loginFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}