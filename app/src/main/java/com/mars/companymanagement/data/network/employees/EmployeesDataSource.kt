package com.mars.companymanagement.data.network.employees

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.common.asRequestResult
import com.mars.companymanagement.data.network.employees.request.AddEmployeeRequest
import com.mars.companymanagement.data.network.employees.request.UpdateEmployeeRequest
import com.mars.companymanagement.data.network.employees.response.EmployeeResponse
import javax.inject.Inject

interface EmployeesDataSource {
    suspend fun getEmployees(): RequestResult<List<EmployeeResponse>>

    suspend fun updateEmployee(request: UpdateEmployeeRequest): RequestResult<EmployeeResponse>

    suspend fun addEmployee(request: AddEmployeeRequest): RequestResult<EmployeeResponse>
}

class EmployeesDataSourceImpl @Inject constructor(
    private val gson: Gson,
    private val employeesApi: EmployeesApi
) : EmployeesDataSource {

    override suspend fun getEmployees(): RequestResult<List<EmployeeResponse>> {
        return employeesApi.getEmployees().asRequestResult(gson)
    }

    override suspend fun updateEmployee(request: UpdateEmployeeRequest): RequestResult<EmployeeResponse> {
        return employeesApi.updateEmployee(request).asRequestResult(gson)
    }

    override suspend fun addEmployee(request: AddEmployeeRequest): RequestResult<EmployeeResponse> {
        return employeesApi.addEmployee(request).asRequestResult(gson)
    }
}