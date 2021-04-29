package com.mars.companymanagement.presentation.screens.projects.modify.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.details.CreateProjectData
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectDetails
import com.mars.companymanagement.presentation.screens.projects.modify.models.ProjectChanges
import kotlinx.parcelize.Parcelize

@Parcelize
class CreateProjectBehaviour : ChangeProjectBehaviour {
    override fun getPreliminaryData(): ProjectDetails? = null

    override suspend fun applyChanges(projectsRepository: ProjectsRepository, changes: ProjectChanges): RequestResult<ProjectDetails> {
        val data = changes.createData() ?: return RequestResult.UnknownError()
        return projectsRepository.createProject(data)
    }

    private fun ProjectChanges.createData(): CreateProjectData? {
        return CreateProjectData(
            name ?: return null,
            description ?: return null,
            budget ?: return null,
            statusId ?: return null,
            deadline ?: return null,
            ownerId ?: return null,
            employees?.map { it.id } ?: return null
        )
    }
}