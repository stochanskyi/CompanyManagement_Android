package com.mars.companymanagement.data.repositories.user

import com.mars.companymanagement.data.repositories.user.models.User
import com.mars.companymanagement.data.storages.UserDataModel
import com.mars.companymanagement.data.storages.UserStorage
import java.lang.IllegalStateException
import javax.inject.Inject

interface UserRepository {
    fun getUser(): User?
    fun getToken(): String?
}

class UserRepositoryImpl @Inject constructor(
    private val userStorage: UserStorage
) : UserRepository {

    override fun getUser(): User? = userStorage.getUser()?.parse()

    override fun getToken(): String? = userStorage.getUser()?.token

    private fun UserDataModel.parse(): User {
        return User(
            id = id,
            name = name,
            email = email,
            accessLevel = accessLevel.generateAccessLevel() ?: throw IllegalStateException("Unsupported level ${accessLevel.id}")
        )
    }
}