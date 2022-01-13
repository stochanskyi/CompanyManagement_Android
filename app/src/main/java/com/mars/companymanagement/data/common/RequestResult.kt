package com.mars.companymanagement.data.common

sealed class RequestResult<out T> {
    data class Success<T>(val data: T) : RequestResult<T>()

    open class Error(val message: String) : RequestResult<Nothing>()

    class UnknownError : Error("")

    fun onSuccess(action: (T) -> Unit): RequestResult<T> = apply {
        if (this is Success) action(data)
    }

    suspend fun suspendOnSuccess(action: suspend (T) -> Unit) = apply {
        if (this is Success) action(data)
    }

    suspend fun onError(action: suspend (String) -> Unit) = apply {
        if (this is Error) action(message)
    }

    fun <R> map(action: (T) -> R): RequestResult<R> {
        return when (this) {
            is Success -> Success(action(data))
            is Error -> Error(message)
        }
    }

    fun ignoreValue(): RequestResult<Unit> {
        return map { }
    }
}

inline fun <T> T?.asRequestResult(block: () -> String): RequestResult<T> {
    return this
        ?.let { RequestResult.Success(this) }
        ?: RequestResult.Error(block())
}

fun <T> RequestResult<T?>.takeIfDataNotNull(): RequestResult<T> = when (this) {
    is RequestResult.Error -> this
    is RequestResult.Success -> this.data?.let { RequestResult.Success(it) }
        ?: RequestResult.UnknownError()
}