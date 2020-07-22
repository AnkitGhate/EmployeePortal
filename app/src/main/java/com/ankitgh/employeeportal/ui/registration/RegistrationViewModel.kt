package com.ankitgh.employeeportal.ui.registration

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.utils.Resource

class RegistrationViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    fun registerUser(userSchema: UserSchema): LiveData<Resource<UserSchema>> {
       return  mainRepository.registerUser(userSchema)
    }
}
