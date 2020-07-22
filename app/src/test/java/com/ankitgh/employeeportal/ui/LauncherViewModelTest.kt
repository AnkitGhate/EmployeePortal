package com.ankitgh.employeeportal.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ankitgh.employeeportal.data.MainRepository
import com.ankitgh.employeeportal.util.MainCoroutineRule
import com.ankitgh.employeeportal.util.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LauncherViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    // Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var SUT: LauncherViewModel

    @Mock
    private lateinit var mainRepository: MainRepository

    @Before
    fun setUp() {
        success(mainRepository)
        SUT = LauncherViewModel(mainRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun ifUserRegistered_returnsTrue() {
        SUT.launchDestination.observeForTesting {
            assertEquals(Destination.HOME, SUT.launchDestination.value)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun ifUserRegistered_returnsFalse() {
        failure(mainRepository)
        SUT.launchDestination.observeForTesting {
            assertEquals(Destination.ONBOARDING, SUT.launchDestination.value)
        }
    }

    private fun success(mainRepo: MainRepository) {
        doReturn(true).`when`(mainRepo).isUserAlreadyRegistered()
    }

    private fun failure(mainRepo: MainRepository) {
        doReturn(false).`when`(mainRepo).isUserAlreadyRegistered()
    }
}