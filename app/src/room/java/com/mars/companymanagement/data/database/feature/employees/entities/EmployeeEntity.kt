package com.mars.companymanagement.data.database.feature.employees.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "employee_id") val employeeId: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "position_id") val positionId: String,
    @ColumnInfo(name = "email") val email: String
)