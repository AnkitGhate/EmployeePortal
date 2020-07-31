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

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankitgh.employeeportal.domain.SignInUseCase
import com.ankitgh.employeeportal.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    fun signInUser(email: String, password: String, callback: (Resource<Boolean>) -> Unit) {

        callback(Resource.loading(isloading = true))
        viewModelScope.launch {
            signInUseCase.signInUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    callback(Resource.success(true))
                    callback(Resource.loading(isloading = false))
                }
                .addOnFailureListener {
                    callback(Resource.error(it.message))
                    callback(Resource.loading(isloading = false))
                }
        }
    }
}
