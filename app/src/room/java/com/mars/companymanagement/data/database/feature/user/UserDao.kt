package com.mars.companymanagement.data.database.feature.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mars.companymanagement.data.database.feature.user.entities.AccessLevelEntity
import com.mars.companymanagement.data.database.feature.user.entities.UserEntity
import com.mars.companymanagement.data.database.feature.user.entities.UserWithAccessLevelEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUserByCredentials(email: String, password: String): UserEntity?

    @Query("SELECT * FROM user, access_level WHERE email = :email AND password = :password AND access_level.levelId = user.accessLevelId")
    suspend fun getUserWithAccessLevelByCredentials(email: String, password: String): UserWithAccessLevelEntity?

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Insert
    suspend fun insertAccessLevel(level: AccessLevelEntity)

    @Query("DELETE FROM user")
    suspend fun clearUsers()

    @Query("DELETE FROM access_level")
    suspend fun clearAccessLevels()

    @Transaction
    suspend fun clearUsersAndAccessLevels() {
        clearUsers()
        clearAccessLevels()
    }

}