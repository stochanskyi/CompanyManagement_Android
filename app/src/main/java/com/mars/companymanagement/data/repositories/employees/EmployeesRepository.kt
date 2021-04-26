package com.mars.companymanagement.data.repositories.employees

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.employees.EmployeesDataSource
import com.mars.companymanagement.data.network.employees.models.EmployeeResponse
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition
import com.mars.companymanagement.ext.withIoContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Inject

interface EmployeesRepository {
    suspend fun getEmployees(): RequestResult<List<Employee>>
}

class EmployeesRepositoryImpl @Inject constructor(
    private val employeesDataSource: EmployeesDataSource
): EmployeesRepository {

    override suspend fun getEmployees(): RequestResult<List<Employee>> = withIoContext {
        employeesDataSource.getEmployees().map { it.parse() }
    }

    private fun List<EmployeeResponse>.parse(): List<Employee> {
        return map {
            Employee(
                it.id,
                it.firstName,
                it.lastName,
                it.email,
                it.position.parse()
            )
        }
    }

    private fun EmployeeResponse.EmployeePositionResponse.parse() =  EmployeePosition(id, name)
}