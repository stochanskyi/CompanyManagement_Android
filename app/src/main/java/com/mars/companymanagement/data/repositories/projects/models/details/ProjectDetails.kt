package com.mars.companymanagement.data.repositories.projects.models.details

import android.os.Parcelable
import com.mars.companymanagement.data.repositories.customers.models.Customer
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.projects.models.info.ProjectStatus
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class ProjectDetails(
    val id: String,
    val name: String,
    val description: String,
    val budget: Float,
    val deadline: LocalDate,
    val status: ProjectStatus,
    val projectOwner: Customer,
    val employees: List<Employee> = emptyList()
) : Parcelable