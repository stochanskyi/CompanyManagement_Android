package com.mars.companymanagement.data.repositories.projects.models.details

import java.time.LocalDate

data class CreateProjectData(

    val name: String,

    val description: String,

    val statusId: String,

    val deadline: LocalDate,

    val customerId: String,

    val employeeIds: List<String>
)