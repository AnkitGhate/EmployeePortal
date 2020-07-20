package com.ankitgh.employeeportal.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankitgh.employeeportal.domain.SignInUseCase
import com.ankitgh.employeeportal.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    fun signInUser(email: String, password: String, callback: (Resource<Boolean>) -> Unit) {
        viewModelScope.launch {
            signInUseCase.signInUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    callback(Resource.success(true))
                }
                .addOnFailureListener {
                    callback(Resource.error(it.message))
                }
        }
    }
}
