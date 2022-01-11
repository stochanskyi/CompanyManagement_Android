package com.mars.companymanagement.data.database.feature.taxonomy.positions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PositionDao {

    @Query("SELECT * FROM position")
    suspend fun getPositions(): List<PositionEntity>

    @Insert
    suspend fun insertPositions(positions: List<PositionEntity>)

    @Insert
    suspend fun insertPosition(position: PositionEntity)

    @Query("DELETE FROM position")
    suspend fun clearPositions()

}