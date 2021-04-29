package com.mars.companymanagement.data.network.employees.request

import com.google.gson.annotations.SerializedName

data class ChangeSalaryRequest(
    @SerializedName("employeeId")
    val employeeId: Int,
    @SerializedName("projectId")
    val projectId: Int,
    @SerializedName("salary")
    val salary: Float
)