package com.mars.companymanagement.data.network.auth.models.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("level")
    val accessLevel: AccessLevelResponse,

    @SerializedName("token")
    val token: String
) {
    data class AccessLevelResponse(
        @SerializedName("levelId")
        val id: String,

        @SerializedName("levelName")
        val name: String
    )
}