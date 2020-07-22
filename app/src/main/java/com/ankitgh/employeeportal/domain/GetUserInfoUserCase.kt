package com.ankitgh.employeeportal.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUserCase @Inject constructor(private val mainRepository: MainRepository) {

    fun getUser(): LiveData<Resource<UserSchema>> {
        return mainRepository.getUser()
    }
}