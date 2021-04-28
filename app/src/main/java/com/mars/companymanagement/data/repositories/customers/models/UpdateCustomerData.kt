package com.mars.companymanagement.data.repositories.customers.models

import com.google.gson.annotations.SerializedName
import java.util.stream.Stream

data class UpdateCustomerData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val country: String,
    val email: String,
    val phoneNumber: String
)