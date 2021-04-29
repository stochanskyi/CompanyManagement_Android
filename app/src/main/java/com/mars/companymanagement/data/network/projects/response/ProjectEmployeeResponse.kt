package com.mars.companymanagement.data.network.projects.response

import com.google.gson.annotations.SerializedName

data class ProjectEmployeeResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("budgey")
    val budget: Float?,
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