package com.mars.companymanagement.presentation.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.LoginRepository
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private val _loginSuccessLiveData: SingleLiveData<Unit> = SingleLiveData()
    val loginSuccessLiveData: LiveData<Unit> = _loginSuccessLiveData

    private var email: String = ""
    private var password: String = ""

    fun changeEmail(email: String) {
        this.email = email
    }

    fun changePassword(password: String) {
        this.password = password
    }

    fun login() {
        viewModelScope.launch {
            safeRequestCall { loginRepository.login(email, password) }?.let {
                _loginSuccessLiveData.call()
            }
        }
    }
}