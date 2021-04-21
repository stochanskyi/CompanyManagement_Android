package com.mars.companymanagement.data.common

sealed class RequestResult<out T> {
    class Success<T>(val data: T): RequestResult<T>()

    open class Error(val message: String) : RequestResult<Nothing>()

    class UnknownError : Error("")

    fun onSuccess(action: (T) -> Unit) {
        if (this is Success) action(data)
    }

    fun onError(action: (String) -> Unit) {
        if (this is Error) action(message)
    }

    fun <R> map(action: (T) -> R): RequestResult<R> {
        return when (this) {
            is Success -> Success(action(data))
            is Error -> Error(message)
        }
    }
}

fun <T> RequestResult<T?>.takeIfDataNotNull(): RequestResult<T> {
    return return when (this) {
        is RequestResult.Error -> this
        is RequestResult.Success -> this.data?.let { RequestResult.Success(it) }
            ?: RequestResult.UnknownError()
    }
}