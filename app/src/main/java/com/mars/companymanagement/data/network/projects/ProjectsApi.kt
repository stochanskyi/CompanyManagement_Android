package com.mars.companymanagement.data.network.projects

import com.mars.companymanagement.data.network.projects.response.ProjectDetailsResponse
import com.mars.companymanagement.data.network.projects.response.ProjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProjectsApi {

    @GET("projects")
    suspend fun getProjects(): Response<List<ProjectResponse>>

    @GET("employees/employee_project")
    suspend fun getEmployeeProjects(@Query("employeeId") employeeId: Int): Response<List<ProjectResponse>>

    @GET("projects/details")
    suspend fun getProjectDetails(@Query("projectId") projectId: Int): Response<ProjectDetailsResponse>

}