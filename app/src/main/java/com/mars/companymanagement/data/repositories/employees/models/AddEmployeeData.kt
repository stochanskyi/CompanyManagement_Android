package com.mars.companymanagement.data.repositories.employees.models

data class AddEmployeeData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val positionId: String
)