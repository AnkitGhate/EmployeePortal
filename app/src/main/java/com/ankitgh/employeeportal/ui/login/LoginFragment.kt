package com.ankitgh.employeeportal.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.Status
import com.ankitgh.employeeportal.common.isValidEmail
import com.ankitgh.employeeportal.common.isValidPassword
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var onBoardingNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_input_edittext.doAfterTextChanged {
            if (email_input_layout.isErrorEnabled) email_input_layout.error = null
        }

        password_input_edittext.doAfterTextChanged {
            if (password_inputlayout.isErrorEnabled) password_inputlayout.error = null
        }

        onBoardingNavController = Navigation.findNavController(view)

        signin_button.setOnClickListener {
            val email: String = email_input_edittext.text.toString()
            val password: String = password_input_edittext.text.toString()
            progressBar.visibility = View.VISIBLE

            if (validateUser(email, password)) {
                viewModel.signInUser(email, password) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            navigateTo(R.id.action_loginFragment_to_homeActivity)
                            requireActivity().finish()
                        }
                        Status.ERROR -> {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginFragment", "signInWithEmail:${it.message}")
                            Snackbar.make(requireView(), "Authentication Failed", Snackbar.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                        }
                        Status.LOADING -> TODO()
                        Status.UNKNOWN -> TODO()
                    }
                }
            }
        }
        regsiter_button.setOnClickListener {
            navigateTo(R.id.registrationFragment)
        }
    }

    private fun validateUser(email: String, password: String): Boolean {
        var result = true
        if (!email.isValidEmail()) {
            email_input_layout.error = "Please enter a valid email"
            result = false
        }
        if (!password.isValidPassword()) {
            password_inputlayout.error = "Please enter a valid password"
            result = false
        }
        return result
    }

    private fun navigateTo(resId: Int) {
        onBoardingNavController.navigate(resId)

    }
}
