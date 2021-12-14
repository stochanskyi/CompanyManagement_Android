package com.mars.companymanagement.app.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object AppDispatchers {
    val Main: CoroutineDispatcher get() = Dispatchers.Main
    val IO: CoroutineDispatcher get() = Dispatchers.IO
    val Default: CoroutineDispatcher get() =  Dispatchers.Default
    val Unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}