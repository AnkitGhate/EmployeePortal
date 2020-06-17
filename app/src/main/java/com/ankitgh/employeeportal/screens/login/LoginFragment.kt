package com.ankitgh.employeeportal.screens.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.isValidEmail
import com.ankitgh.employeeportal.common.isValidPassword
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var onBoardingNavController: NavController
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = Firebase.auth
        return inflater.inflate(R.layout.login_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

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
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginFragment", "signInWithEmail:success")
                            val user = firebaseAuth.currentUser
                            onBoardingNavController.navigate(R.id.action_loginFragment_to_homeActivity)
                            requireActivity().finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginFragment", "signInWithEmail:failure", task.exception)
                            Snackbar.make(
                                requireView(),
                                "Authentication Failed",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        progressBar.visibility = View.GONE
                    }
            }
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

}
