package com.mars.companymanagement.presentation.screens.login

import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mars.companymanagement.R
import com.mars.companymanagement.data.repositories.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.components.SingletonComponent
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.RETURNS_MOCKS
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
@HiltAndroidTest
class LoginActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val repo: LoginRepository = mock(LoginRepository::class.java)

    @BindValue
    @JvmField
    val loginViewModel: LoginViewModel = LoginViewModel(repo)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test() {
//        Mockito.`when`(loginViewModel.toString()).thenReturn("TEST")
        val scenario = launchActivity<LoginActivity>()

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            activity.findViewById<Button>(R.id.submit_button).performClick()

            assertTrue(activity.findViewById<Button>(R.id.submit_button).hasOnClickListeners())
//            Mockito.verify(loginViewModel).login()
//            verify { loginViewModel.login() }
        }
    }
}