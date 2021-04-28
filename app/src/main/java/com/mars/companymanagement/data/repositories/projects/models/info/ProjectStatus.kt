package com.mars.companymanagement.data.repositories.projects.models.info

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectStatus(
    val id: String,
    val name: String
) : Parcelable