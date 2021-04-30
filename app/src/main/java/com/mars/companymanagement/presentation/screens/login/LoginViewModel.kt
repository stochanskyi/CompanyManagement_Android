package com.mars.companymanagement.presentation.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.data.repositories.login.LoginRepository
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private val _loginLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loginLoadingLiveData: LiveData<Boolean> = _loginLoadingLiveData

    private val _loginSuccessLiveData: SingleLiveData<Unit> = SingleLiveData()
    val loginSuccessLiveData: LiveData<Unit> = _loginSuccessLiveData

    private val _emailNotValidLiveData: SingleLiveData<Unit> = SingleLiveData()
    val emailNotValidLiveData: LiveData<Unit> = _emailNotValidLiveData

    private val _passwordNotValidLiveData: SingleLiveData<Unit> = SingleLiveData()
    val passwordNotValidLiveData: LiveData<Unit> = _passwordNotValidLiveData

    private var email: String = ""
    private var password: String = ""

    fun changeEmail(email: String) {
        this.email = email
    }

    fun changePassword(password: String) {
        this.password = password
    }

    fun login() {
        var inputsValid = true

        if (email.isBlank()) {
            inputsValid = false
            _emailNotValidLiveData.call()
        }

        if (password.isBlank()) {
            inputsValid = false
            _passwordNotValidLiveData.call()
        }

        if (!inputsValid) return

        viewModelScope.launch {
            safeRequestCallWithLoading(_loginLoadingLiveData) { loginRepository.login(email, password) }?.let {
                _loginSuccessLiveData.call()
            }
        }
    }
}