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
