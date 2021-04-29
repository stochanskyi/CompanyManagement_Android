package com.mars.companymanagement.data.repositories.projects.models.details

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class UpdateProjectData(

    val projectId: String,

    val name: String,

    val description: String,

    val budget: Float,

    val statusId: String,

    val deadline: LocalDate,

    val customerId: String,

    val employeeIds: List<String>

)