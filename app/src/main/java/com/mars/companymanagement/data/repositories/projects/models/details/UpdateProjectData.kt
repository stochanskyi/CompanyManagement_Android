package com.mars.companymanagement.data.repositories.projects.models.details

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class UpdateProjectData(

    val projectId: String,

    val projectName: String,

    val description: String,

    val statusId: Int,

    val deadline: LocalDate,

    val customerId: Int,

    val employeeIds: List<Int>

)