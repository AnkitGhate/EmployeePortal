package com.ankitgh.employeeportal.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.util.MainCoroutineRule
import com.ankitgh.employeeportal.util.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock

class LauncherViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    // Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var SUT: LauncherViewModel

    private val mainRepository = mock(MainRepository::class.java)

    @Before
    fun setUp() {
        success(mainRepository)
        SUT = LauncherViewModel(mainRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun launchDestination_ifUserRegistered_returnsTrue() {
        SUT.launchDestination.observeForTesting {
            Assert.assertEquals(SUT.launchDestination.value, Destination.HOME)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun launchDestination_ifUserRegistered_returnsFalse() {
        failure(mainRepository)
        SUT.launchDestination.observeForTesting {
            Assert.assertEquals(SUT.launchDestination.value, Destination.ONBOARDING)
        }
    }

    private fun success(mainRepo: MainRepository) {
        doReturn(true).`when`(mainRepo).isUserAlreadyRegistered()
    }

    private fun failure(mainRepo: MainRepository) {
        doReturn(false).`when`(mainRepo).isUserAlreadyRegistered()
    }
}