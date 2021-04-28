package com.mars.companymanagement.data.network.projects.response

import com.google.gson.annotations.SerializedName
import com.mars.companymanagement.data.network.customers.response.response.CustomerResponse
import com.mars.companymanagement.data.network.employees.response.EmployeeResponse

data class ProjectDetailsResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("projectName")
	val name: String,

	@field:SerializedName("deadline")
	val deadline: String,

	@field:SerializedName("budget")
	val budget: Float,

	@field:SerializedName("customer")
	val customer: CustomerResponse,

	@field:SerializedName("status")
	val status: ProjectResponse.ProjectStatusResponse,

	@field:SerializedName("employees")
	val employees: List<EmployeeResponse>

)