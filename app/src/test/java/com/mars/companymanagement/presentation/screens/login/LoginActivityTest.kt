package com.mars.companymanagement.presentation.screens.login

import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mars.companymanagement.R
import com.mars.utils.espresso.*
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(
    application = HiltTestApplication::class,
)
@HiltAndroidTest
class LoginActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val loginViewModel: LoginViewModel = mockk(relaxed = true)

    @Test
    fun test() {

        val activityScenario = createLoginScenario()

        activityScenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.submit_button)).perform(click())

        activityScenario.onActivity { activity ->
            assertTrue(activity.findViewById<Button>(R.id.submit_button).hasOnClickListeners())
        }
        verify { loginViewModel.login() }
    }

    @Test
    fun `When login loading change, if is loading, then hide inputs and buttons, and show progress bar`() {
        val mockLoadingLiveData = MutableLiveData<Boolean>()
        every { loginViewModel.loginLoadingLiveData } returns mockLoadingLiveData

        val activityScenario = createLoginScenario()
        activityScenario.moveToState(Lifecycle.State.RESUMED)

        mockLoadingLiveData.value = true

        onViewsWithIds(
            R.id.submit_button,
            R.id.email_layout,
            R.id.password_layout,
        ).checkAllMatches(isNotCompletelyDisplayed())

        onViewWithId(R.id.progressBar).checkMatches(isCompletelyDisplayed())
    }

    private fun createLoginScenario() = launchTestActivity(LoginActivity::class)
}