package com.mars.companymanagement.data.network.common.adapter

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.common.asRequestResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RequestResultCallWrapper<T>(
    private val call: Call<T>,
    private val gson: Gson
) : Call<RequestResult<T>> {

    override fun clone(): Call<RequestResult<T>> {
        return RequestResultCallWrapper(call.clone(), gson)
    }

    override fun execute(): Response<RequestResult<T>> = throw IllegalStateException(
        "Synchronous execute is not supported for ${this::class.simpleName}"
    )

    override fun enqueue(callback: Callback<RequestResult<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = response.asRequestResult(gson)

                callback.onResponse(this@RequestResultCallWrapper, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = RequestResult.Error(t.message ?: "")

                callback.onResponse(this@RequestResultCallWrapper, Response.success(result))
            }

        })
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()
}