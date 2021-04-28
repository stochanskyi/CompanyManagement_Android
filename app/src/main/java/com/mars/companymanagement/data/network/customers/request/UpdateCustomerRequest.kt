package com.mars.companymanagement.data.network.customers.request

import com.google.gson.annotations.SerializedName

data class UpdateCustomerRequest(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("firstName")
	val firstName: String,

	@field:SerializedName("lastName")
	val lastName: String,

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String
)
