package com.ankitgh.employeeportal.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.common.NetworkUtil
import com.ankitgh.employeeportal.data.repository.MainReporsitory

class HomeViewModel @ViewModelInject constructor(
    private val mainRepository: MainReporsitory,
    private val networkHelper: NetworkUtil
) : ViewModel()