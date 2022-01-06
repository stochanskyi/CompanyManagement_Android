package com.mars.companymanagement.data.database.feature.taxonomy.positions

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "position")
data class PositionEntity(
    @PrimaryKey(autoGenerate = true) val positionId: Int,
    val name: String
)