package com.mars.companymanagement.data.database.common.initialization

import com.mars.companymanagement.data.database.common.CompanyDatabase
import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionEntity
import com.mars.companymanagement.data.database.feature.user.entities.AccessLevelEntity
import com.mars.companymanagement.data.database.feature.user.entities.UserEntity

interface DatabaseInitializer {

    suspend fun init(database: CompanyDatabase)

}

/**
 * Initializes database with default data
 * Should be replaced with actions with DAOs as params
 */
class DatabaseInitializerImpl : DatabaseInitializer {

    override suspend fun init(database: CompanyDatabase) {

        val userDao = database.userDao()
        val positionsDao = database.positionsDao()

        userDao.clearUsersAndAccessLevels()

        userDao.insertAccessLevel(
            AccessLevelEntity(
                levelId = 1,
                levelName = "Owner"
            )
        )

        userDao.insertUser(
            UserEntity(
                userName = "User Name",
                email = "test@t.t",
                password = "test",
                accessLevelId = 1
            )
        )

        positionsDao.clearPositions()

        positionsDao.insertPosition(
            PositionEntity(
                name = "Android developer"
            )
        )
    }

}
