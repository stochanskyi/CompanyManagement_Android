package com.mars.companymanagement.data.repositories.customers.models

data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val country: String,
    val email: String,
    val phoneNumber: String
) {
    val fullName: String get() = "$firstName $lastName"
}