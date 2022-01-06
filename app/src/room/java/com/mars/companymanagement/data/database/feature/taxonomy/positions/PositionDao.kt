package com.mars.companymanagement.data.database.feature.taxonomy.positions

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PositionDao {

    @Query("SELECT * FROM position")
    suspend fun getPositions(): List<PositionEntity>

}