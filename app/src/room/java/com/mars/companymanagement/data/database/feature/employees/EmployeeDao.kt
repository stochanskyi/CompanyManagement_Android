package com.mars.companymanagement.data.database.feature.employees

import androidx.room.*
import com.mars.companymanagement.data.database.feature.employees.entities.EmployeeEntity
import com.mars.companymanagement.data.database.feature.employees.entities.EmployeeWithPositionEntity

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    @Transaction
    suspend fun getEmployeesWithPositions(): List<EmployeeWithPositionEntity>

    @Query("SELECT * FROM employee WHERE employee_id = :employeeId")
    @Transaction
    suspend fun getEmployeeWithPositions(employeeId: Int): EmployeeWithPositionEntity

    @Insert
    suspend fun insertEmployee(employee: EmployeeEntity): Long

    @Update
    suspend fun updateEmployee(employee: EmployeeEntity)

    @Delete
    suspend fun deleteEmployee(employee: EmployeeEntity)
}