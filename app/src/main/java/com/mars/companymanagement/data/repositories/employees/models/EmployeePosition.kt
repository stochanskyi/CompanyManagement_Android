package com.mars.companymanagement.data.repositories.employees.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeePosition(
    val id: String,
    val name: String
) : Parcelable