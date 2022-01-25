package com.mars.companymanagement.data.network.auth

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.auth.models.request.LoginRequest
import com.mars.companymanagement.data.network.auth.models.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): RequestResult<LoginResponse>
}