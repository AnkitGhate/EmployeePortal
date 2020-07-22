package com.ankitgh.employeeportal.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.Status
import com.ankitgh.employeeportal.utils.isValidEmail
import com.ankitgh.employeeportal.utils.isValidPassword
import com.google.android.material.snackbar.Snackbar
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
                        showSnackBar(getString(R.string.authentication_failed_message))
                        Timber.d("signInWithEmail:${it.message}")
                    }
                    Status.LOADING -> if (it.isloading) login_progressBar.visibility = View.VISIBLE else login_progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun showSnackBar(message: String) {
        val snackBar: Snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
            .setTextColor(ContextCompat.getColor(requireActivity(), R.color.white_50))
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
        snackBar.show()
    }

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