package com.mars.companymanagement.data.repositories.projects.models.details

import java.time.LocalDate

data class ProjectDetails(
    val id: String,
    val name: String,
    val description: String,
    val budget: Float,
    val deadline: LocalDate,
    val projectOwner: ProjectOwner
)