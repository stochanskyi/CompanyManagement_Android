package com.mars.companymanagement.data.network.projects

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.common.asRequestResult
import com.mars.companymanagement.data.network.projects.response.ProjectDetailsResponse
import com.mars.companymanagement.data.network.projects.response.ProjectResponse
import javax.inject.Inject

interface ProjectsDataSource {
    suspend fun getProjects(): RequestResult<List<ProjectResponse>>
    suspend fun getProjectsForEmployee(employeeId: Int): RequestResult<List<ProjectResponse>>
    suspend fun getProjectDetails(projectId: Int): RequestResult<ProjectDetailsResponse>
    suspend fun getCustomerProjects(customerId: Int): RequestResult<List<ProjectResponse>>
}

class ProjectsDataSourceImpl @Inject constructor(
    private val projectsApi: ProjectsApi,
    private val gson: Gson
): ProjectsDataSource {

    override suspend fun getProjects(): RequestResult<List<ProjectResponse>> {
        return projectsApi.getProjects().asRequestResult(gson)
    }

    override suspend fun getProjectsForEmployee(employeeId: Int): RequestResult<List<ProjectResponse>> {
        return projectsApi.getEmployeeProjects(employeeId).asRequestResult(gson)
    }

    override suspend fun getProjectDetails(projectId: Int): RequestResult<ProjectDetailsResponse> {
        return projectsApi.getProjectDetails(projectId).asRequestResult(gson)
    }

    override suspend fun getCustomerProjects(customerId: Int): RequestResult<List<ProjectResponse>> {
        return projectsApi.getCustomerProjects(customerId).asRequestResult(gson)
    }
}