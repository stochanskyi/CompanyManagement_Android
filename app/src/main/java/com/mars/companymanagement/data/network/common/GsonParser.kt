package com.mars.companymanagement.data.network.common

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.common.takeIfDataNotNull
import retrofit2.Response
import java.io.Reader
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
        errorBody()?.let {
            val error: ErrorResponse? = gson.fromJsonOrNull(it.charStream())
            RequestResult.Error(error?.errorMessage ?: return RequestResult.UnknownError())
        } ?: RequestResult.UnknownError()
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

inline fun <reified T> Gson.fromJsonOrNull(json: JsonElement): T? {
    return try {
        fromJson(json, T::class.java)
    } catch (e: Throwable) {
        null
    }
}

inline fun <reified T> Gson.fromJsonOrNull(json: String): T? {
    return try {
        fromJson(json, T::class.java)
    } catch (e: Throwable) {
        null
    }

}

inline fun <reified T> Gson.fromJsonOrNull(json: Reader): T? {
    return try {
        fromJson(json, T::class.java)
    } catch (e: Throwable) {
        null
    }
}
