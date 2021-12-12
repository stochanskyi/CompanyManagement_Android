package com.mars.utils.espresso

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import kotlin.reflect.KClass

fun onViews(vararg matchers: Matcher<View>): List<ViewInteraction> {
    return matchers.map { onView(it) }
}

fun List<ViewInteraction>.checkAll(assertion: ViewAssertion): List<ViewInteraction> {
    return map { it.check(assertion) }
}

fun isNotDisplayed(): Matcher<View> {
    return not(isDisplayed())
}

fun isNotCompletelyDisplayed(): Matcher<View> = not(isCompletelyDisplayed())

fun onViewWithId(@IdRes id: Int): ViewInteraction {
    return onView(withId(id))
}

fun onViewsWithIds(@IdRes vararg ids: Int): List<ViewInteraction> {
    return ids.map { onViewWithId(it) }
}

fun ViewInteraction.checkMatches(matcher: Matcher<View>): ViewInteraction {
    return check(matches(matcher))
}

fun List<ViewInteraction>.checkAllMatches(matcher: Matcher<View>): List<ViewInteraction> {
    return map { it.checkMatches(matcher) }
}

fun <T : Activity> launchTestActivity(activityClass: KClass<T>): ActivityScenario<T> {
    return ActivityScenario.launch(activityClass.java)
}