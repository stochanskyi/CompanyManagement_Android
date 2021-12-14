package com.mars.companymanagement.presentation.screens.login

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mars.companymanagement.R
import com.mars.companymanagement.presentation.screens.main.MainActivity
import com.mars.utils.EspressoMainDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LoginActivityAndroidTest {

    @Before
    fun setup() {
        val idlingResource = CountingIdlingResource("Main Dispatcher")

        Dispatchers.setMain(EspressoMainDispatcher(Dispatchers.Main, idlingResource))
        IdlingRegistry.getInstance().register(idlingResource)

        launchActivity<LoginActivity>().apply {
            moveToState(Lifecycle.State.RESUMED)
        }
    }

    @Test
    fun whenLogin_IfEmailIsEmpty_ThenShowEmailValidationError() {
        onView(withId(R.id.email_editText)).perform(replaceText(""))
        onView(withId(R.id.password_editText)).perform(replaceText("Aa123456"))

        onView(withId(R.id.submit_button)).perform(click())

        onView(withText(R.string.email_validation_error)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun whenLogin_IfPasswordIsEmpty_ThenShowPasswordValidationError() {
        onView(withId(R.id.email_editText)).perform(replaceText("test@test.com"))
        onView(withId(R.id.password_editText)).perform(replaceText(""))

        onView(withId(R.id.submit_button)).perform(click())

        onView(withText(R.string.password_validation_error)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun whenLogin_IfPasswordAndEmailAreEmpty_ThenShowPasswordAndEmailValidationErrors() {
        onView(withId(R.id.email_editText)).perform(replaceText(""))
        onView(withId(R.id.password_editText)).perform(replaceText(""))

        onView(withId(R.id.submit_button)).perform(click())

        onView(withText(R.string.email_validation_error)).check(matches(isCompletelyDisplayed()))
        onView(withText(R.string.password_validation_error)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun whenLogin_IfInvalidCredentials_ThenShowError() {
        onView(withId(R.id.email_editText)).perform(replaceText("ads@c.c"))
        onView(withId(R.id.password_editText)).perform(replaceText("afsdkjagnfsds"))

        onView(withId(R.id.submit_button)).perform(click())

        onView(withText("Error")).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun whenLogin_IfValidCredentials_ThenOpenMainScreen() {
        onView(withId(R.id.email_editText)).perform(replaceText("robert.ad@cm.com"))
        onView(withId(R.id.password_editText)).perform(replaceText("Aa12345"))

        onView(withId(R.id.submit_button)).perform(click())

        // TODO solve issue with blocking dispatcher when observing flow
        intended(toPackage(MainActivity::class.qualifiedName))
    }

}