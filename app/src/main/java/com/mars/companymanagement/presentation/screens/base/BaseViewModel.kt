package com.mars.companymanagement.presentation.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mars.companymanagement.data.common.RequestResult

abstract class BaseViewModel : ViewModel() {

    private val _requestErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val requestErrorLiveData: MutableLiveData<String?> = _requestErrorLiveData

    protected suspend fun <T> safeRequestCallWithLoading(loadingLiveData: MutableLiveData<Boolean>, safeCall: suspend () -> RequestResult<T>): T? {
        loadingLiveData.value = true
        return safeRequestCall(safeCall).also { loadingLiveData.value = false }
    }

    protected suspend fun <T> safeRequestCall(safeCall: suspend () -> RequestResult<T>): T? {
        return try {
            processRequestResult(safeCall())
        } catch (e: Throwable) {
            if (handleCallError()) return null

            showError("")
            null
        }
    }

    private fun <T> processRequestResult(result: RequestResult<T>): T? {
        return when (result) {
            is RequestResult.Success -> return result.data
            is RequestResult.Error -> {
                if (handleSafeCallError(result)) return null

                showError(result.message)
                null
            }
        }
    }

    protected open fun handleSafeCallError(error: RequestResult.Error): Boolean = false

    protected open fun handleCallError(): Boolean = false

    protected open fun showError(message: String) {

    }
}