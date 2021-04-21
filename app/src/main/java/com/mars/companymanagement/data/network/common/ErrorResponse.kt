package com.mars.companymanagement.data.network.common

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val errorMessage: String
)