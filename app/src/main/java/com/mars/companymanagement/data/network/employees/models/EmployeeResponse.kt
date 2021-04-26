package com.mars.companymanagement.data.network.employees.models

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("position")
    val position: EmployeePositionResponse
) {
    data class EmployeePositionResponse(
        @SerializedName("positionId")
        val id: String,
        @SerializedName("positionName")
        val name: String
    )
}