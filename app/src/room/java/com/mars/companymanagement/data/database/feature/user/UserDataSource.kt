package com.mars.companymanagement.data.database.feature.user

import com.mars.companymanagement.data.database.feature.user.entities.UserWithAccessLevelEntity
import javax.inject.Inject

interface UserDataSource {
    suspend fun getUserWithAccessLevelByCredentials(email: String, password: String): UserWithAccessLevelEntity?
}

class UserDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserDataSource {

    override suspend fun getUserWithAccessLevelByCredentials(email: String, password: String): UserWithAccessLevelEntity? {
        return userDao.getUserWithAccessLevelByCredentials(email, password)
    }

}