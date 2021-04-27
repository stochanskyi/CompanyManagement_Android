package com.mars.companymanagement.data.repositories.projects

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.projects.ProjectsDataSource
import com.mars.companymanagement.data.network.projects.models.ProjectResponse
import com.mars.companymanagement.data.repositories.projects.models.Project
import com.mars.companymanagement.data.repositories.projects.models.ProjectStatus
import com.mars.companymanagement.ext.withIoContext
import javax.inject.Inject

interface ProjectsRepository {
    suspend fun getProjects(): RequestResult<List<Project>>

    suspend fun getProjectsForEmployee(employeeId: String): RequestResult<List<Project>>
}

class ProjectsRepositoryImpl @Inject constructor(
    private val projectsDataSource: ProjectsDataSource
) : ProjectsRepository {

    override suspend fun getProjects(): RequestResult<List<Project>> = withIoContext {
        projectsDataSource.getProjects().map { it.parse() }
    }

    override suspend fun getProjectsForEmployee(employeeId: String): RequestResult<List<Project>> =
        withIoContext { projectsDataSource.getProjectsForEmployee(employeeId.toInt()).map { it.parse() } }

    private fun List<ProjectResponse>.parse() = map { it.parse() }

    private fun ProjectResponse.parse() = Project(id, name, status.parse())

    private fun ProjectResponse.ProjectStatusResponse.parse() = ProjectStatus(id, name)

}