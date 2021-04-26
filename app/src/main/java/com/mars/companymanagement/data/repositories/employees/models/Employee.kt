package com.mars.companymanagement.data.repositories.employees.models

data class Employee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val position: EmployeePosition
) {
    val fullName: String get() = "$firstName $lastName"
}