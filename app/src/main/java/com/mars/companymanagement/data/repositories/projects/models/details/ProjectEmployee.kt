package com.mars.companymanagement.data.repositories.projects.models.details

import android.os.Parcelable
import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectEmployee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val salary: Float?,
    val email: String,
    val position: EmployeePosition
) : Parcelable {
    val fullName: String get() = "$firstName $lastName"
}