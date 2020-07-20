package com.ankitgh.employeeportal.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ankitgh.employeeportal.data.MainRepository

enum class Destination {
    ONBOARDING, HOME
}

class LauncherViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val launchDestination = liveData {
        if (mainRepository.isUserAlreadyRegistered())
            emit(Destination.HOME)
        else {
            emit(Destination.ONBOARDING)
        }
    }
}

