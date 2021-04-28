package com.mars.companymanagement.presentation.screens.projects.modify.behaviour

import android.os.Parcelable
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectDetails
import com.mars.companymanagement.presentation.screens.projects.modify.models.ProjectChanges

interface ChangeProjectBehaviour : Parcelable {

    fun getPreliminaryData(): ProjectDetails?

    suspend fun applyChanges(projectsRepository: ProjectsRepository, changes: ProjectChanges): RequestResult<ProjectDetails>
}