package com.mars.companymanagement.data.repositories.customers.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val country: String,
    val email: String,
    val phoneNumber: String
) : Parcelable{
    val fullName: String get() = "$firstName $lastName"
}