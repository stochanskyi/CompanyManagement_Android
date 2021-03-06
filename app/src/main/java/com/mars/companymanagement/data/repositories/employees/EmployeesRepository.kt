package com.mars.companymanagement.data.repositories.employees

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.employees.EmployeesDataSource
import com.mars.companymanagement.data.network.employees.request.AddEmployeeRequest
import com.mars.companymanagement.data.network.employees.request.ChangeSalaryRequest
import com.mars.companymanagement.data.network.employees.request.UpdateEmployeeRequest
import com.mars.companymanagement.data.network.employees.response.EmployeeResponse
import com.mars.companymanagement.data.repositories.employees.models.AddEmployeeData
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition
import com.mars.companymanagement.data.repositories.employees.models.UpdateEmployeeData
import com.mars.companymanagement.ext.withIoContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

interface EmployeesRepository {
    val employeeUpdatedFlow: Flow<Employee>
    val employeeAddedFlow: Flow<Employee>

    suspend fun getEmployees(): RequestResult<List<Employee>>
    suspend fun updateEmployeeInfo(data: UpdateEmployeeData): RequestResult<Employee>
    suspend fun createEmployee(data: AddEmployeeData): RequestResult<Employee>
    suspend fun setSalary(employeeId: String, projectId: String, salary: Float): RequestResult<Unit>
}

class EmployeesRepositoryImpl @Inject constructor(
    private val employeesDataSource: EmployeesDataSource
) : EmployeesRepository {
    override val employeeUpdatedFlow: MutableSharedFlow<Employee> = MutableSharedFlow()

    override val employeeAddedFlow: MutableSharedFlow<Employee> = MutableSharedFlow()

    override suspend fun getEmployees(): RequestResult<List<Employee>> = withIoContext {
        employeesDataSource.getEmployees().map { it.parse() }
    }

    override suspend fun updateEmployeeInfo(data: UpdateEmployeeData): RequestResult<Employee> =
        withIoContext {
            employeesDataSource.updateEmployee(data.createRequest())
                .map { it.parse() }
                .suspendOnSuccess { employeeUpdatedFlow.emit(it) }
        }

    override suspend fun createEmployee(data: AddEmployeeData): RequestResult<Employee> {
        return withIoContext {
            employeesDataSource.addEmployee(data.createRequest())
                .map { it.parse() }
                .suspendOnSuccess { employeeAddedFlow.emit(it) }
        }
    }

    override suspend fun setSalary(
        employeeId: String,
        projectId: String,
        salary: Float
    ): RequestResult<Unit> {
        return withIoContext {
            employeesDataSource.setSalary(ChangeSalaryRequest(employeeId.toInt(), projectId.toInt(), salary))
        }
    }

    private fun UpdateEmployeeData.createRequest() = UpdateEmployeeRequest(
        id,
        firstName,
        lastName,
        email,
        positionId.toInt()
    )

    private fun AddEmployeeData.createRequest() = AddEmployeeRequest(
        firstName, lastName, email, positionId.toInt()
    )

    private fun List<EmployeeResponse>.parse(): List<Employee> {
        return map { it.parse() }
    }

    private fun EmployeeResponse.parse() =
        Employee(
            id,
            firstName,
            lastName,
            email,
            position.parse()
        )

    private fun EmployeeResponse.EmployeePositionResponse.parse() = EmployeePosition(id, name)
}