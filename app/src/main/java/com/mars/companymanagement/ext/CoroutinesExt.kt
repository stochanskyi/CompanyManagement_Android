package com.mars.companymanagement.ext

import com.mars.companymanagement.app.dispatchers.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

suspend fun <T> withIoContext(block: suspend CoroutineScope.() -> T): T =
    withContext(AppDispatchers.IO, block)

suspend fun <T> withComputationContext(block: suspend CoroutineScope.() -> T): T =
    withContext(AppDispatchers.Default, block)

suspend fun <T> withMainContext(block: suspend CoroutineScope.() -> T): T =
    withContext(AppDispatchers.Main, block)

suspend fun <T> withUnconfinedContext(block: suspend CoroutineScope.() -> T): T =
    withContext(AppDispatchers.Unconfined, block)