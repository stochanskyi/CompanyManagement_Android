package com.mars.companymanagement.data.network.employees

import com.mars.companymanagement.data.network.employees.request.AddEmployeeRequest
import com.mars.companymanagement.data.network.employees.request.UpdateEmployeeRequest
import com.mars.companymanagement.data.network.employees.response.EmployeeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EmployeesApi {
    @GET("employees")
    suspend fun getEmployees(): Response<List<EmployeeResponse>>

    @POST("employees/update")
    suspend fun updateEmployee(@Body request: UpdateEmployeeRequest): Response<EmployeeResponse>

    @POST("employees/add")
    suspend fun addEmployee(@Body request: AddEmployeeRequest): Response<EmployeeResponse>
}