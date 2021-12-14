package com.mars.utils

import android.util.Log
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

class EspressoMainDispatcher(
    private val dispatcher: MainCoroutineDispatcher,
    val idlingResource: CountingIdlingResource,
) : MainCoroutineDispatcher() {

    var singleTaskOnly: Boolean = false

    override val immediate: MainCoroutineDispatcher =
        if (dispatcher.immediate === dispatcher) this else EspressoMainDispatcher(
            dispatcher.immediate,
            idlingResource
        )
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        context[Job]?.let { addNewJob(it) }
        dispatcher.dispatch(context, block)
    }

    @InternalCoroutinesApi
    override fun dispatchYield(context: CoroutineContext, block: Runnable) {
        context[Job]?.let { addNewJob(it) }
        dispatcher.dispatchYield(context, block)
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
        return dispatcher.isDispatchNeeded(context)
    }
}