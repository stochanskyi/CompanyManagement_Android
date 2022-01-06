package com.mars.companymanagement.data.database.feature.taxonomy.project

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProjectStatusDao {

    @Query("SELECT * FROM project_status")
    suspend fun getProjectStatuses(): List<ProjectStatusEntity>

}