package com.mars.companymanagement.presentation.screens.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.login.LoginRepository
import com.mars.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    companion object {
        private const val TEST_EMAIL = "test.email@mail.com"
        private const val TEST_PASSWORD = "Aa123456_"
    }

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val coroutinesRule = MainDispatcherRule(TestCoroutineDispatcher())

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var passwordNotValidObserver: Observer<Unit>

    @Mock
    private lateinit var emailNotValidObserver: Observer<Unit>

    @Mock
    private lateinit var loginLoadingObserver: Observer<Boolean>

    @Mock
    private lateinit var loginSuccessObserver: Observer<Unit>

    @Mock
    private lateinit var requestErrorObserver: Observer<String?>

    private val loginRepository: LoginRepository = mock()

    private val loginViewModel: LoginViewModel = LoginViewModel(loginRepository)

    @Before
    fun setUp() {
        loginViewModel.apply {
            emailNotValidLiveData.observeForever(emailNotValidObserver)
            passwordNotValidLiveData.observeForever(passwordNotValidObserver)
            loginLoadingLiveData.observeForever(loginLoadingObserver)
            loginSuccessLiveData.observeForever(loginSuccessObserver)
            requestErrorLiveData.observeForever(requestErrorObserver)
        }
    }

    @After
    fun tearDown() {
        loginViewModel.apply {
            emailNotValidLiveData.removeObserver(emailNotValidObserver)
            passwordNotValidLiveData.removeObserver(passwordNotValidObserver)
            loginLoadingLiveData.removeObserver(loginLoadingObserver)
            loginSuccessLiveData.removeObserver(loginSuccessObserver)
            requestErrorLiveData.removeObserver(requestErrorObserver)
        }
    }

    @Test
    fun `When login, if email and password valid, then call login on repository`() {
        setCredentials()

        loginViewModel.login()

        verifyBlocking(loginRepository) { login(TEST_EMAIL, TEST_PASSWORD) }
    }

    @Test
    fun `When login, if password invalid, then call invalid password live data`() {
        loginViewModel.changeEmail(TEST_EMAIL)

        loginViewModel.login()

        verify(passwordNotValidObserver).onChanged(anyOrNull())

        verifyBlocking(loginRepository, never()) { login(any(), any()) }
    }

    @Test
    fun `When login, if email invalid, then call invalid email live data`() {
        loginViewModel.changePassword(TEST_PASSWORD)

        loginViewModel.login()

        verify(emailNotValidObserver).onChanged(anyOrNull())

        verifyBlocking(loginRepository, never()) { login(any(), any()) }
    }

    @Test
    fun test() {
        setCredentials()

        loginViewModel.login()

        loginLoadingObserver.inOrder {
            verify().onChanged(true)
            verify().onChanged(false)
        }
    }

    @Test
    fun `When login, if login request error with message, then call request error live data with message`() {
        val errorMessage = "Test error message"

        setCredentials()

        loginRepository.stub {
            onBlocking { login(any(), any()) }.thenReturn(RequestResult.Error(errorMessage))
        }

        loginViewModel.login()

        verify(requestErrorObserver).onChanged(errorMessage)
    }

    @Test
    fun `When login, if login request success, then call login success live data`() {
        setCredentials()

        loginRepository.stub {
            onBlocking { login(any(), any()) }.thenReturn(RequestResult.Success(Unit))
        }

        loginViewModel.login()

        verify(loginSuccessObserver).onChanged(anyOrNull())
    }

    private fun setCredentials() {
        loginViewModel.changeEmail(TEST_EMAIL)
        loginViewModel.changePassword(TEST_PASSWORD)
    }

}