package com.mars.companymanagement.data.repositories.employees

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.database.feature.employees.RoomEmployeeDataSource
import com.mars.companymanagement.data.database.feature.employees.entities.EmployeeEntity
import com.mars.companymanagement.data.database.feature.employees.entities.EmployeeWithPositionEntity
import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionEntity
import com.mars.companymanagement.data.repositories.employees.models.AddEmployeeData
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition
import com.mars.companymanagement.data.repositories.employees.models.UpdateEmployeeData
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class RoomEmployeesRepository @Inject constructor(
    private val employeesDataSource: RoomEmployeeDataSource
) : EmployeesRepository {

    override val employeeUpdatedFlow: MutableSharedFlow<Employee> = MutableSharedFlow()
    override val employeeAddedFlow: MutableSharedFlow<Employee> = MutableSharedFlow()

    override suspend fun getEmployees(): RequestResult<List<Employee>> {
        return RequestResult.Success(
            employeesDataSource.getEmployeesWithPositions().map { it.parse() }
        )
    }

    override suspend fun updateEmployeeInfo(data: UpdateEmployeeData): RequestResult<Employee> {
        employeesDataSource.updateEmployee(data.toEntity())

        val employee = employeesDataSource.getEmployeeById(data.id.toInt()).parse()

        employeeUpdatedFlow.emit(employee)
        return RequestResult.Success(employee)
    }

    override suspend fun createEmployee(data: AddEmployeeData): RequestResult<Employee> {
        val id = employeesDataSource.insertEmployee(data.toEntity())

        val employee = employeesDataSource.getEmployeeById(id.toInt()).parse()
        employeeAddedFlow.emit(employee)

        return RequestResult.Success(employee)
    }

    override suspend fun setSalary(
        employeeId: String,
        projectId: String,
        salary: Float
    ): RequestResult<Unit> {
        //TODO

        return RequestResult.Success(Unit)
    }
}

private fun EmployeeWithPositionEntity.parse(): Employee {
    return Employee(
        id = employee.employeeId.toString(),
        firstName = employee.firstName,
        lastName = employee.lastName,
        email = employee.email,
        position = position.parse()
    )
}

private fun PositionEntity.parse(): EmployeePosition {
    return EmployeePosition(
        id = positionId.toString(),
        name = name
    )
}

private fun UpdateEmployeeData.toEntity(): EmployeeEntity {
    return EmployeeEntity(
        id.toInt(),
        firstName,
        lastName,
        email,
        positionId
    )
}

private fun AddEmployeeData.toEntity(): EmployeeEntity {
    return EmployeeEntity(
        firstName = firstName,
        lastName = lastName,
        email = email,
        positionId = positionId
    )
}