package com.mars.companymanagement.data.network.auth

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.auth.models.request.LoginRequest
import com.mars.companymanagement.data.network.auth.models.response.LoginResponse
import com.mars.companymanagement.data.network.common.asRequestResult
import javax.inject.Inject

interface LoginDataSource {
    fun login(request: LoginRequest): RequestResult<LoginResponse>
}

class LoginDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
    private val gson: Gson
) : LoginDataSource {

    override fun login(request: LoginRequest): RequestResult<LoginResponse> {
        return authApi.login(request).asRequestResult(gson)
    }
}