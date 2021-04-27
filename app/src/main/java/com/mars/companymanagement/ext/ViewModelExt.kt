package com.mars.companymanagement.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.launchSafeCall(loadingLiveData: MutableLiveData<Boolean>, task: suspend () -> Unit) {
    launch {
        withMainContext { loadingLiveData.value = true }
        task()
        withMainContext { loadingLiveData.value = false }
    }
}