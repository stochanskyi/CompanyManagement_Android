package com.mars.companymanagement.data.repositories.customers.models

data class CreateCustomerData(
    val firstName: String,
    val lastName: String,
    val country: String,
    val email: String,
    val phoneNumber: String
)