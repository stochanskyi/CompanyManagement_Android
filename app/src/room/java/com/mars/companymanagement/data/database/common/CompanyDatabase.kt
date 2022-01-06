package com.mars.companymanagement.data.database.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionDao
import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionEntity
import com.mars.companymanagement.data.database.feature.taxonomy.project.ProjectStatusDao
import com.mars.companymanagement.data.database.feature.taxonomy.project.ProjectStatusEntity
import com.mars.companymanagement.data.database.feature.user.UserDao
import com.mars.companymanagement.data.database.feature.user.entities.AccessLevelEntity
import com.mars.companymanagement.data.database.feature.user.entities.UserEntity

@Database(
    entities = [UserEntity::class, PositionEntity::class, ProjectStatusEntity::class, AccessLevelEntity::class],
    version = 1
)
abstract class CompanyDatabase : RoomDatabase() {

    companion object {
        fun create(appContext: Context): CompanyDatabase {
            return Room
                .databaseBuilder(
                    appContext,
                    CompanyDatabase::class.java,
                    "cm-db"
                )
                .build()
        }
    }

    abstract fun userDao(): UserDao

    abstract fun projectStatusDao(): ProjectStatusDao

    abstract fun positionsDao(): PositionDao

}