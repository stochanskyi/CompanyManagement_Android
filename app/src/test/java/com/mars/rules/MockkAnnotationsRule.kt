package com.mars.rules

import io.mockk.MockKAnnotations
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockkAnnotationsRule(
    private vararg val obj: Any,
    private val overrideRecordPrivateCalls: Boolean = false,
    private val relaxUnitFun: Boolean = false,
    private val relaxed: Boolean = false
) : TestWatcher() {

    override fun starting(description: Description?) {
        MockKAnnotations.init(
            *obj,
            overrideRecordPrivateCalls = overrideRecordPrivateCalls,
            relaxUnitFun = relaxUnitFun,
            relaxed = relaxed)
    }

}