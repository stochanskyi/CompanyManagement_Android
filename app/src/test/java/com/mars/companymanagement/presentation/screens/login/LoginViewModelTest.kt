package com.mars.companymanagement.presentation.screens.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mars.companymanagement.data.repositories.login.LoginRepository
import com.mars.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking

import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val loginRepository = Mockito.mock(LoginRepository::class.java)

    val loginViewModel = LoginViewModel(loginRepository)

    @get:Rule
    val coroutinesRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test() {
        loginViewModel.changeEmail("testMail")
        loginViewModel.changePassword("TestPass")

        loginViewModel.login()

        runBlocking { Mockito.verify(loginRepository).login("testMail", "TestPass") }
    }

//    @Before
//    fun setUp() {
//        Init
//    }
}