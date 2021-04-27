package com.mars.companymanagement.data.network.projects

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.common.asRequestResult
import com.mars.companymanagement.data.network.projects.models.ProjectResponse
import javax.inject.Inject

interface ProjectsDataSource {
    suspend fun getProjects(): RequestResult<List<ProjectResponse>>
}

class ProjectsDataSourceImpl @Inject constructor(
    private val projectsApi: ProjectsApi,
    private val gson: Gson
): ProjectsDataSource {

    override suspend fun getProjects(): RequestResult<List<ProjectResponse>> {
        return projectsApi.getProjects().asRequestResult(gson)
    }
}