package com.mars.companymanagement.data.network.projects.request

import com.google.gson.annotations.SerializedName

data class UpdateProjectRequest(

	@field:SerializedName("projectId")
	val projectId: Int,

	@field:SerializedName("employeeIds")
	val employeeIds: List<Int>,

	@field:SerializedName("statusId")
	val statusId: Int,

	@field:SerializedName("customerId")
	val customerId: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("projectName")
	val projectName: String,

	@field:SerializedName("deadline")
	val deadline: String
)
