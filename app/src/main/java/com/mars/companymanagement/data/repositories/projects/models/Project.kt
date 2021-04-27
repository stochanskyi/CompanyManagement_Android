package com.mars.companymanagement.data.repositories.projects.models

data class Project(
    val id: String,
    val name: String,
    val status: ProjectStatus
)