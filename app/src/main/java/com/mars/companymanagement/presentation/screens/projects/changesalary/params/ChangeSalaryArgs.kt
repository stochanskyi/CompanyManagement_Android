package com.mars.companymanagement.presentation.screens.projects.changesalary.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangeSalaryArgs(
    val name: String,
    val position: String,
    val salary: Float
) : Parcelable