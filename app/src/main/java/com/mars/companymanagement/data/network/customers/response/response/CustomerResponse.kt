package com.mars.companymanagement.data.network.customers.response.response

import com.google.gson.annotations.SerializedName

data class CustomerResponse(

	@SerializedName("firstName")
	val firstName: String,

	@SerializedName("lastName")
	val lastName: String,

	@SerializedName("country")
	val country: String,

	@SerializedName("phoneNumber")
	val phoneNumber: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("email")
	val email: String
)