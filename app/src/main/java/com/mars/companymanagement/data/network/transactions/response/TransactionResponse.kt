package com.mars.companymanagement.data.network.transactions.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(

	@SerializedName("date")
	val date: String,

	@SerializedName("amount")
	val amount: Float,

	@SerializedName("isIncome")
	val isIncome: Boolean,

	@SerializedName("person")
	val person: String,

	@SerializedName("id")
	val id: Int
)
