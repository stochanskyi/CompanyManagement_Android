package com.mars.companymanagement.data.network.employees.request

import com.google.gson.annotations.SerializedName

class UpdateEmployeeRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("positionId")
    val positionId: Int
)