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

package com.ankitgh.employeeportal.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.Status
import com.ankitgh.employeeportal.utils.isValidEmail
import com.ankitgh.employeeportal.utils.isValidPassword
import com.ankitgh.employeeportal.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import timber.log.Timber

/**
 * Entry point for user to login to application with registered credentials or
 * register new account
 */
@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var onBoardingNavController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoardingNavController = Navigation.findNavController(view)
        login_button_register.setOnClickListener(this)
        login_button_signin.setOnClickListener(this)
        observeEditFieldsAndClearOnEdit()
    }

    private fun observeEditFieldsAndClearOnEdit() {
        login_email_input_edittext.doAfterTextChanged {
            if (login_email_input_layout.isErrorEnabled) login_email_input_layout.error = null
        }
        login_password_input_edittext.doAfterTextChanged {
            if (login_password_inputlayout.isErrorEnabled) login_password_inputlayout.error = null
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            login_button_signin -> initSignIn()
            login_button_register -> navigateTo(R.id.registrationFragment)
        }
    }

    private fun initSignIn() {
        val email = login_email_input_edittext.text.toString()
        val password = login_password_input_edittext.text.toString()
        if (validateUser(email, password)) {
            viewModel.signInUser(email, password) {
                when (it.status) {
                    Status.SUCCESS -> {
                        navigateTo(R.id.action_loginFragment_to_homeActivity)
                        requireActivity().finish()
                    }
                    Status.ERROR -> {
                        clearFieldsData()
                        showSnackBar(requireView(), getString(R.string.authentication_failed_message))
                        Timber.d("signInWithEmail:${it.message}")
                    }
                    Status.LOADING -> if (it.isloading) login_progressBar.visibility = View.VISIBLE else login_progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun clearFieldsData() {
        login_email_input_edittext.text?.clear()
        login_password_input_edittext.text?.clear()
    }

//    private fun showSnackBar(message: String) {
//        val snackBar: Snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
//            .setTextColor(ContextCompat.getColor(requireActivity(), R.color.white_50))
//        val snackBarView = snackBar.view
//        snackBarView.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
//        snackBar.show()
//    }

    private fun validateUser(email: String, password: String): Boolean {
        var result = true
        if (!email.isValidEmail()) {
            login_email_input_layout.error = "Please enter a valid email"
            result = false
        }
        if (!password.isValidPassword()) {
            login_password_inputlayout.error = "Please enter a valid password"
            result = false
        }
        return result
    }

    private fun navigateTo(resId: Int) {
        onBoardingNavController.navigate(resId)
    }

}