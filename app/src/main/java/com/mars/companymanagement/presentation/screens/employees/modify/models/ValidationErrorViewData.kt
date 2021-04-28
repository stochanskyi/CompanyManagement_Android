package com.mars.companymanagement.presentation.screens.employees.modify.models

data class ValidationErrorViewData(
    val firstNameValidationError: String? = null,
    val lastNameValidationError: String? = null,
    val emailValidationError: String? = null,
    val positionValidationError: String? = null
) {

    val isDataValid: Boolean get() = firstNameValidationError != null
        && lastNameValidationError != null && emailValidationError != null && positionValidationError != null

}