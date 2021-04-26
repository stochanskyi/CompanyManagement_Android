package com.mars.companymanagement.data.network.employees

import com.mars.companymanagement.data.network.employees.models.EmployeeResponse
import retrofit2.Response
import retrofit2.http.GET

interface EmployeesApi {
    @GET("employees")
    suspend fun getEmployees(): Response<List<EmployeeResponse>>
}