package com.mars.companymanagement.data.network.transactions.response

import com.google.gson.annotations.SerializedName

data class CustomerDetailResponse(

	@field:SerializedName("amount")
	val amount: Float	,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("paymentDate")
	val paymentDate: String,

	@field:SerializedName("customer")
	val customer: Customer,

	@field:SerializedName("confirmer")
	val confirmer: ConfirmerResponse
) {

	data class Customer(

		@field:SerializedName("firstName")
		val firstName: String,

		@field:SerializedName("lastName")
		val lastName: String,

		@field:SerializedName("country")
		val country: String,

		@field:SerializedName("id")
		val id: Int
	)
}