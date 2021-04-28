package com.mars.companymanagement.presentation.screens.projects.modify.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectDetails
import com.mars.companymanagement.presentation.screens.projects.modify.models.ProjectChanges
import kotlinx.parcelize.Parcelize

@Parcelize
class EditProjectBehaviour(
    private val preliminaryData: ProjectDetails
) : ChangeProjectBehaviour {
    override fun getPreliminaryData(): ProjectDetails = preliminaryData

    override suspend fun applyChanges(projectsRepository: ProjectsRepository, changes: ProjectChanges): RequestResult<ProjectDetails> {
        return RequestResult.UnknownError()
    }
}