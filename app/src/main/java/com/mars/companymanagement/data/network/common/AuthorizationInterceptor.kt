package com.mars.companymanagement.data.network.common

import com.mars.companymanagement.data.storages.user.UserStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val userStorage: UserStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request()
            .newBuilder()
//            .addHeader("Content-Type", "application/json")

        val token = userStorage.getUser()?.token

        if (token.isNullOrBlank().not()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
    }
}