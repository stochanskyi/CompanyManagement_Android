package com.mars.companymanagement.presentation.screens.projects.modify.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectDetails
import com.mars.companymanagement.presentation.screens.projects.modify.models.ProjectChanges
import kotlinx.parcelize.Parcelize

@Parcelize
class CreateProjectBehaviour : ChangeProjectBehaviour {
    override fun getPreliminaryData(): ProjectDetails? = null

    override suspend fun applyChanges(projectsRepository: ProjectsRepository, changes: ProjectChanges): RequestResult<ProjectDetails> {
        //TODO
        return RequestResult.UnknownError()
    }

}