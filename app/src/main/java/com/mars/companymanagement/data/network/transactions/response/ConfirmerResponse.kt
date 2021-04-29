package com.mars.companymanagement.data.network.transactions.response

import com.google.gson.annotations.SerializedName


data class ConfirmerResponse(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)