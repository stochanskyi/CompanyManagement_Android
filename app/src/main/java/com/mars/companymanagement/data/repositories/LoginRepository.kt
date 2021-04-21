package com.mars.companymanagement.data.repositories

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.auth.LoginDataSource
import com.mars.companymanagement.data.network.auth.models.request.LoginRequest
import com.mars.companymanagement.data.network.auth.models.response.LoginResponse
import com.mars.companymanagement.data.storages.UserDataModel
import com.mars.companymanagement.data.storages.UserStorage
import com.mars.companymanagement.ext.withIoContext
import javax.inject.Inject

interface LoginRepository {
    suspend fun login(email: String, password: String): RequestResult<Unit>
}

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val userStorage: UserStorage
) : LoginRepository {

    override suspend fun login(email: String, password: String) = withIoContext {
        loginDataSource.login(LoginRequest(email, password))
            .suspendOnSuccess{ withIoContext { userStorage.setUser(it.toDataModel()) } }
            .ignoreValue()
    }

    private fun LoginResponse.toDataModel(): UserDataModel {
        return UserDataModel(
            id, name, email, UserDataModel.AccessLevel(accessLevel.id, accessLevel.name), token
        )
    }

}