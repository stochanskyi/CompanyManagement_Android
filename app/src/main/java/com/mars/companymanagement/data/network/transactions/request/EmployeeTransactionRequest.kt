package com.mars.companymanagement.data.network.transactions.request

import com.google.gson.annotations.SerializedName

data class EmployeeTransactionRequest(

	@field:SerializedName("employeeId")
	val employeeId: Int,

	@field:SerializedName("amount")
	val amount: Float,

	@field:SerializedName("typeId")
	val typeId: Int? = null
)
