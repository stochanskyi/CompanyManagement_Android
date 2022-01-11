package com.mars.companymanagement.data.database.feature.employees

import com.mars.companymanagement.data.database.feature.employees.entities.EmployeeEntity
import com.mars.companymanagement.data.database.feature.employees.entities.EmployeeWithPositionEntity
import javax.inject.Inject

interface RoomEmployeeDataSource {

    suspend fun getEmployeesWithPositions(): List<EmployeeWithPositionEntity>

    suspend fun getEmployeeById(id: Int): EmployeeWithPositionEntity

    suspend fun insertEmployee(employee: EmployeeEntity): Long

    suspend fun updateEmployee(employee: EmployeeEntity)

    suspend fun deleteEmployee(employee: EmployeeEntity)

}

class RoomEmployeeDataSourceImpl @Inject constructor(
    private val employeeDao: EmployeeDao
) : RoomEmployeeDataSource {

    override suspend fun getEmployeesWithPositions(): List<EmployeeWithPositionEntity> {
        return employeeDao.getEmployeesWithPositions()
    }

    override suspend fun getEmployeeById(id: Int): EmployeeWithPositionEntity {
        return employeeDao.getEmployeeWithPositions(id)
    }

    override suspend fun insertEmployee(employee: EmployeeEntity): Long {
        return employeeDao.insertEmployee(employee)
    }

    override suspend fun updateEmployee(employee: EmployeeEntity) {
        employeeDao.updateEmployee(employee)
    }

    override suspend fun deleteEmployee(employee: EmployeeEntity) {
        employeeDao.deleteEmployee(employee)
    }

}