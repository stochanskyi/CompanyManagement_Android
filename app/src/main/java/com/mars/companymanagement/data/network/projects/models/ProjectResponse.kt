package com.mars.companymanagement.data.network.projects.models

import com.google.gson.annotations.SerializedName

data class ProjectResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("status")
    val status: ProjectStatusResponse
) {
    data class ProjectStatusResponse(
        @SerializedName("statusId")
        val id: String,
        @SerializedName("statusName")
        val name: String
    )
}