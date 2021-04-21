package com.mars.companymanagement.data.network.auth.models.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val email: String,

    @SerializedName("password")
    val password: String
)