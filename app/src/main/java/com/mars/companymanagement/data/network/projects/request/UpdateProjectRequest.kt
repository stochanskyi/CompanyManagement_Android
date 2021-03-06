package com.mars.companymanagement.data.network.projects.request

import com.google.gson.annotations.SerializedName

data class UpdateProjectRequest(

	@field:SerializedName("projectId")
	val projectId: Int,

	@field:SerializedName("projectName")
	val projectName: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("budget")
	val budget: Float,

	@field:SerializedName("customerId")
	val customerId: Int,

	@field:SerializedName("statusId")
	val statusId: Int,

	@field:SerializedName("deadline")
	val deadline: String,

	@field:SerializedName("employeeIds")
	val employeeIds: List<Int>
)
