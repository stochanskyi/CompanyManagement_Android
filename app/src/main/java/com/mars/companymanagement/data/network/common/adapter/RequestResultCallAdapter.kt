package com.mars.companymanagement.data.network.common.adapter

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RequestResultCallAdapter<T>(
    private val type: Type,
    private val gson: Gson
) : CallAdapter<T, Call<RequestResult<T>>> {

    override fun responseType(): Type {
        return type
    }

    override fun adapt(call: Call<T>): Call<RequestResult<T>> {
        return RequestResultCallWrapper(call, gson)
    }

}