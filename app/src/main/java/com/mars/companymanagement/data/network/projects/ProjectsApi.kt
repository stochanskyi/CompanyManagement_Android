package com.mars.companymanagement.data.network.projects

import com.mars.companymanagement.data.network.projects.models.ProjectResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProjectsApi {

    @GET("projects")
    suspend fun getProjects(): Response<List<ProjectResponse>>
}