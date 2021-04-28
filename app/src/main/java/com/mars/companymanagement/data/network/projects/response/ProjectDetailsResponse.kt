package com.mars.companymanagement.data.network.projects.response

import com.google.gson.annotations.SerializedName

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
	val customer: CustomerResponse
) {
	data class CustomerResponse(

		@field:SerializedName("firstName")
		val firstName: String,

		@field:SerializedName("lastName")
		val lastName: String,

		@field:SerializedName("country")
		val country: String,

		@field:SerializedName("custId")
		val id: Int
	)
}