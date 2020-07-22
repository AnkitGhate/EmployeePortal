package com.ankitgh.employeeportal.ui.login

import com.ankitgh.employeeportal.data.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Due to direct firebase calls testing these classes needs more hacky implementeatino
 * TODO : Will come back later on this.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @Mock
    lateinit var mainRepository: MainRepository

    @Before
    fun setUp() {
    }
}