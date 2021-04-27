package com.mars.companymanagement.data.network.employees

import com.google.gson.Gson
import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.common.asRequestResult
import com.mars.companymanagement.data.network.employees.models.EmployeeResponse
import com.mars.companymanagement.ext.withIoContext
import javax.inject.Inject

interface EmployeesDataSource {
    suspend fun getEmployees(): RequestResult<List<EmployeeResponse>>
}

class EmployeesDataSourceImpl @Inject constructor(
    private val gson: Gson,
    private val employeesApi: EmployeesApi
) : EmployeesDataSource {

    override suspend fun getEmployees(): RequestResult<List<EmployeeResponse>> = withIoContext {
        employeesApi.getEmployees().asRequestResult(gson)
    }

}