package com.mars.companymanagement.data.database.feature.taxonomy.project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_status")
data class ProjectStatusEntity(
    @PrimaryKey(autoGenerate = true) val statusId: Int = 0,
    val name: String
)