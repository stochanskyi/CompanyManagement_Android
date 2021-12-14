package com.mars.utils

import android.util.Log
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EspressoDispatcher(
    private val coroutineDispatcher: CoroutineDispatcher,
    val idlingResource: CountingIdlingResource
) : CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        context[Job]?.let { addNewJob(it) }
        coroutineDispatcher.dispatch(context, block)
    }

    @InternalCoroutinesApi
    override fun dispatchYield(context: CoroutineContext, block: Runnable) {
        context[Job]?.let { addNewJob(it) }
        coroutineDispatcher.dispatchYield(context, block)
    }

    private fun addNewJob(job: Job) {
        Log.d(idlingResource.name, "Increment")
        idlingResource.increment()
        job.invokeOnCompletion {
            Log.d(idlingResource.name, "Decrement")
            idlingResource.decrement()
        }
    }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        context[Job]?.let { addNewJob(it) }
        return coroutineDispatcher.isDispatchNeeded(context)
    }

}