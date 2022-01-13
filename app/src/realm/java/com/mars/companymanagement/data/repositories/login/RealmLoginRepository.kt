package com.mars.companymanagement.data.repositories.login

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.common.asRequestResult
import com.mars.companymanagement.data.database.features.login.AccessLevelDTO
import com.mars.companymanagement.data.database.features.login.RealmUserDataSource
import com.mars.companymanagement.data.database.features.login.UserDTO
import com.mars.companymanagement.data.storages.user.UserDataModel
import com.mars.companymanagement.data.storages.user.UserStorage
import java.lang.Exception
import javax.inject.Inject

class RealmLoginRepository @Inject constructor(
    private val userDataSource: RealmUserDataSource,
    private val userStorage: UserStorage
) : LoginRepository {


    override suspend fun login(email: String, password: String): RequestResult<Unit> {
        return userDataSource.getUser(email, password)
            .asRequestResult { "" }
            .suspendOnSuccess { userStorage.setUser(it.parse()) }
            .ignoreValue()
    }

    private fun UserDTO.parse(): UserDataModel {
        return UserDataModel(
            id.toString(),
            username,
            email,
            accessLevel?.parse() ?: throw Exception(),
            ""
        )
    }

    private fun AccessLevelDTO.parse(): UserDataModel.AccessLevelDataModel {
        return UserDataModel.AccessLevelDataModel(
            id.toString(),
            name
        )
    }

}