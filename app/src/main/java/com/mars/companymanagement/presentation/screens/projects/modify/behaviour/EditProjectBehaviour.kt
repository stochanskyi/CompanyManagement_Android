package com.mars.companymanagement.presentation.screens.projects.modify.behaviour

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.models.details.CreateProjectData
import com.mars.companymanagement.data.repositories.projects.models.details.ProjectDetails
import com.mars.companymanagement.data.repositories.projects.models.details.UpdateProjectData
import com.mars.companymanagement.presentation.screens.projects.modify.models.ProjectChanges
import kotlinx.parcelize.Parcelize

@Parcelize
class EditProjectBehaviour(
    private val preliminaryData: ProjectDetails
) : ChangeProjectBehaviour {
    override fun getPreliminaryData(): ProjectDetails = preliminaryData

    override suspend fun applyChanges(projectsRepository: ProjectsRepository, changes: ProjectChanges): RequestResult<ProjectDetails> {
        val data = changes.createData() ?: return RequestResult.UnknownError()
        return projectsRepository.updateProject(data)
    }

    private fun ProjectChanges.createData(): UpdateProjectData? {
        return UpdateProjectData(
            preliminaryData.id,
            name ?: return null,
            description ?: return null,
            statusId ?: return null,
            deadline ?: return null,
            ownerId ?: return null,
            employees?.map { it.id } ?: return null
        )
    }
}