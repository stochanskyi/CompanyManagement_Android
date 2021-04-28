package com.mars.companymanagement.presentation.screens.projects.employeeselection.models

data class SelectableEmployeeViewData(
    val id: String,
    val name: String,
    val position: String,
    var isChecked: Boolean
)