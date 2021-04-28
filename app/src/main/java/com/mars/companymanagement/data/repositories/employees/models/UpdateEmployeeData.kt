package com.mars.companymanagement.data.repositories.employees.models

data class UpdateEmployeeData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val positionId: String
)