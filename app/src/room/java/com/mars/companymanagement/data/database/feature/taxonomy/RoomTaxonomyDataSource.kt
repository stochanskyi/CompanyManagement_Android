package com.mars.companymanagement.data.database.feature.taxonomy

import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionDao
import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionEntity
import com.mars.companymanagement.data.database.feature.taxonomy.project.ProjectStatusDao
import com.mars.companymanagement.data.database.feature.taxonomy.project.ProjectStatusEntity
import javax.inject.Inject

interface RoomTaxonomyDataSource {
    suspend fun getPositions(): List<PositionEntity>
    suspend fun getProjectStatuses(): List<ProjectStatusEntity>
}

class RoomTaxonomyDataSourceImpl @Inject constructor(
    private val positionDao: PositionDao,
    private val projectStatusDao: ProjectStatusDao
) : RoomTaxonomyDataSource {

    override suspend fun getPositions(): List<PositionEntity> {
        return positionDao.getPositions()
    }

    override suspend fun getProjectStatuses(): List<ProjectStatusEntity> {
        return projectStatusDao.getProjectStatuses()
    }

}