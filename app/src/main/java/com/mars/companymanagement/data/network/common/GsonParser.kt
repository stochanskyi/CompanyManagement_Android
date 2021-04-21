package com.mars.companymanagement.data.network.common

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.common.takeIfDataNotNull
import retrofit2.Response
import java.lang.reflect.Type

typealias EmptyResponse = Response<Unit>
typealias EmptyRequestResult = RequestResult<Unit>

fun <T> Response<out T?>.asRequestResult(gson: Gson): RequestResult<T> {
    return asRequestResultOrNullData(gson).takeIfDataNotNull()
}

fun <T> Response<out T?>.asRequestResultOrNullData(gson: Gson): RequestResult<T?> {
    return if (isSuccessful) {
        RequestResult.Success(body())
    } else {
        val type = object : TypeToken<ErrorResponse>() {}.type
        errorBody()?.let {
            val error: ErrorResponse? = gson.fromJson(it.charStream(), type)
            RequestResult.Error(error?.errorMessage ?: "")
        } ?: RequestResult.Error("")
    }
}

fun EmptyResponse.asEmptyRequestState(gson: Gson): EmptyRequestResult {
    return if (isSuccessful) {
        RequestResult.Success(Unit)
    } else {
        val type = object : TypeToken<ErrorResponse>() {}.type
        errorBody()?.let {
            val error: ErrorResponse? = gson.fromJson(it.charStream(), type)
            RequestResult.Error(error?.errorMessage ?: "")
        }
    } ?: RequestResult.Error("")
}

fun <T> Gson.fromJsonOrNull(json: JsonElement, type: Type): T? {
    return try {
        fromJson(json, type)
    } catch (e: Throwable) {
        null
    }
}

fun <T> Gson.fromJsonOrNull(json: String, type: Type): T? {
    return try {
        fromJson(json, type)
    } catch (e: Throwable) {
        null
    }
}