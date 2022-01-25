package com.mars.companymanagement.data.network.common.adapter

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RequestResultCallAdapterFactory(
    private val gson: Gson
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        try {
            val callType = (returnType as? ParameterizedType) ?: return null

            if (callType.rawType != Call::class.java) return null

            val callInnerType = callType.firstParametrizedType() ?: return null

            if (callInnerType.rawType != RequestResult::class.java) return null

            val responseType = callInnerType.firstType() ?: Any::class.java

            return RequestResultCallAdapter<Any?>(responseType, gson)


        } catch (e: Throwable) {
            return null
        }

    }

    private fun ParameterizedType.firstParametrizedType(): ParameterizedType? {
        return firstType() as? ParameterizedType
    }

    private fun ParameterizedType.firstType(): Type? {
        return actualTypeArguments.firstOrNull()
    }
}