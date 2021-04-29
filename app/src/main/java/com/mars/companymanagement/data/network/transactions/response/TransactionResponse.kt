package com.mars.companymanagement.data.network.transactions.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("amount")
	val amount: Float,

	@field:SerializedName("isIncome")
	val isIncome: Boolean,

	@field:SerializedName("person")
	val person: String,

	@field:SerializedName("id")
	val id: Int
)
