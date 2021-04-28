package com.mars.companymanagement.presentation.screens.customers.modify.models

data class CustomerValidationErrorViewData(
    val firstNameValidationError: String? = null,
    val lastNameValidationError: String? = null,
    val countryValidationError: String? = null,
    val emailValidationError: String? = null,
    val phoneNumberValidationError: String? = null
)