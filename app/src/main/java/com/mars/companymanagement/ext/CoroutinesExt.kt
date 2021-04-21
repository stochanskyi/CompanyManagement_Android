package com.mars.companymanagement.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> withIoContext(block: suspend CoroutineScope.() -> T): T =
    withContext(Dispatchers.IO, block)

suspend fun <T> withComputationContext(block: suspend CoroutineScope.() -> T): T =
    withContext(Dispatchers.Default, block)

suspend fun <T> withMainContext(block: suspend CoroutineScope.() -> T): T =
    withContext(Dispatchers.Main, block)


suspend fun <T> withUnconfinedContext(block: suspend CoroutineScope.() -> T): T =
    withContext(Dispatchers.Unconfined, block)