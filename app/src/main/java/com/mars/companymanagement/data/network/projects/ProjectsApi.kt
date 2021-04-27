package com.mars.companymanagement.data.network.projects

import com.mars.companymanagement.data.network.projects.models.ProjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProjectsApi {

    @GET("projects")
    suspend fun getProjects(): Response<List<ProjectResponse>>

    @GET("employees/employee_project")
    suspend fun getEmployeeProjects(@Query("employeeId") employeeId: Int): Response<List<ProjectResponse>>
}