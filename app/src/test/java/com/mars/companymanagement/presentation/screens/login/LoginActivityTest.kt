package com.mars.companymanagement.presentation.screens.login

import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mars.companymanagement.R
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
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

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    private val activityScenario: ActivityScenario<LoginActivity> get() = activityScenarioRule.scenario

    @BindValue
    @JvmField
    val loginViewModel: LoginViewModel = mockk(relaxed = true)

    @Test
    fun test() {

        activityScenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.submit_button)).perform(click())

        activityScenario.onActivity { activity ->
            assertTrue(activity.findViewById<Button>(R.id.submit_button).hasOnClickListeners())
        }
        verify { loginViewModel.login() }
    }
}