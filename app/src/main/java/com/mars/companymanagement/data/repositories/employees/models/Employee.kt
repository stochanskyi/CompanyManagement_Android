package com.mars.companymanagement.data.repositories.employees.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val position: EmployeePosition
) : Parcelable {
    val fullName: String get() = "$firstName $lastName"
}