package com.mars.companymanagement.data.database.feature.user

import androidx.room.Dao
import androidx.room.Query
import com.mars.companymanagement.data.database.feature.user.entities.UserEntity
import com.mars.companymanagement.data.database.feature.user.entities.UserWithAccessLevelEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUserByCredentials(email: String, password: String): UserEntity?

    @Query("SELECT * FROM user, access_level WHERE email = :email AND password = :password AND access_level.levelId = user.accessLevelId")
    suspend fun getUserWithAccessLevelByCredentials(email: String, password: String): UserWithAccessLevelEntity?

}