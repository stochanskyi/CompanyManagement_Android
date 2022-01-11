package com.mars.companymanagement.data.database.feature.employees.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionEntity

data class EmployeeWithPositionEntity(
    @Embedded val employee: EmployeeEntity,

    @Relation(
        parentColumn = "employee_id",
        entityColumn = "positionId"
    )
    val position: PositionEntity
)