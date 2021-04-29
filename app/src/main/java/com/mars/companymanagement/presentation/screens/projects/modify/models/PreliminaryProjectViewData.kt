package com.mars.companymanagement.presentation.screens.projects.modify.models

data class PreliminaryProjectViewData(
    val name: String,
    val description: String,
    val budget: String,
    val deadline: String,
    val customerName: String,
    val status: String
)