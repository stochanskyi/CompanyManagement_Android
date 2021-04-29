package com.mars.companymanagement.data.network.transactions.request

import com.google.gson.annotations.SerializedName

data class CustomerTransacctionRequest(

	@field:SerializedName("customerId")
	val customerId: Int,

	@field:SerializedName("projectId")
	val projectId: Int,

	@field:SerializedName("amount")
	val amount: Float
)
