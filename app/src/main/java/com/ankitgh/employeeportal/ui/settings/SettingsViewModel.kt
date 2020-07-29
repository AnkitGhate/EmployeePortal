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

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private var _currentuser = MutableLiveData<UserSchema>()
    var currentUser: LiveData<UserSchema> = _currentuser

    init {
        viewModelScope.launch {
            _currentuser = getCurrentUser()
        }
    }

    private suspend fun getCurrentUser(): MutableLiveData<UserSchema> {
        mainRepository.getCurrentAuthUser().collect {
            _currentuser.value = UserSchema(photoUrl = it.photoUrl)
        }
        return _currentuser
    }
}
