package com.mars.companymanagement.data.database.feature.user.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "access_level")
data class AccessLevelEntity(
    @PrimaryKey(autoGenerate = true) val levelId: Int = -1,
    val levelName: String
)