package com.mars.companymanagement.data.network.transactions.response

import com.google.gson.annotations.SerializedName

data class EmployeeDetailsResponse(

	@field:SerializedName("amount")
	val amount: Float,

	@field:SerializedName("receiver")
	val receiver: Receiver,

	@field:SerializedName("paymentId")
	val paymentId: Int,

	@field:SerializedName("paymentDate")
	val paymentDate: String,

	@field:SerializedName("confirmer")
	val confirmer: ConfirmerResponse
) {
	data class Receiver(

		@field:SerializedName("firstName")
		val firstName: String,

		@field:SerializedName("lastName")
		val lastName: String,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("position")
		val position: String
	)
}