package com.mars.companymanagement.data.network.projects

import com.mars.companymanagement.data.network.projects.request.CreateProjectRequest
import com.mars.companymanagement.data.network.projects.request.UpdateProjectRequest
import com.mars.companymanagement.data.network.projects.response.ProjectDetailsResponse
import com.mars.companymanagement.data.network.projects.response.ProjectResponse
import com.mars.companymanagement.data.repositories.projects.models.info.Project
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProjectsApi {

    @GET("projects")
    suspend fun getProjects(): Response<List<ProjectResponse>>

    @GET("employees/employee_project")
    suspend fun getEmployeeProjects(@Query("employeeId") employeeId: Int): Response<List<ProjectResponse>>

    @GET("projects/details")
    suspend fun getProjectDetails(@Query("projectId") projectId: Int): Response<ProjectDetailsResponse>

    @GET("customers/customer_projects")
    suspend fun getCustomerProjects(@Query("customerId") customerId: Int): Response<List<ProjectResponse>>

    @POST("projects/update")
    suspend fun updateProject(@Body request: UpdateProjectRequest): Response<ProjectDetailsResponse>

    @POST("projects/update")
    suspend fun createProject(@Body request: CreateProjectRequest): Response<ProjectDetailsResponse>

}