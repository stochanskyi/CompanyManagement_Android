package com.mars.companymanagement.app.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
object AppDispatchers {
    private val testDispatcher = TestCoroutineDispatcher()

    val Main: CoroutineDispatcher get() = testDispatcher
    val IO: CoroutineDispatcher get() = testDispatcher
    val Default: CoroutineDispatcher get() =  testDispatcher
    val Unconfined: CoroutineDispatcher = testDispatcher

}