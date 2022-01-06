package com.mars.companymanagement.data.database.feature.taxonomy.positions

import javax.inject.Inject

interface PositionsDataSource {
    suspend fun getPositions(): List<PositionEntity>
}

class PositionsDataSourceImpl @Inject constructor(
    private val positionsDao: PositionDao
) : PositionsDataSource {

    override suspend fun getPositions(): List<PositionEntity> {
        return positionsDao.getPositions()
    }

}