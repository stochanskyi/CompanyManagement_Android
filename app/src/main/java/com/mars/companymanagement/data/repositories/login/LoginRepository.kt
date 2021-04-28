package com.mars.companymanagement.data.repositories.login

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.network.auth.LoginDataSource
import com.mars.companymanagement.data.network.auth.models.request.LoginRequest
import com.mars.companymanagement.data.network.auth.models.response.LoginResponse
import com.mars.companymanagement.data.network.taxonomy.TaxonomyDataSource
import com.mars.companymanagement.data.network.taxonomy.models.TaxonomyResponse
import com.mars.companymanagement.data.storages.taxonomy.TaxonomyStorage
import com.mars.companymanagement.data.storages.taxonomy.model.TaxonomyItem
import com.mars.companymanagement.data.storages.user.UserDataModel
import com.mars.companymanagement.data.storages.user.UserStorage
import com.mars.companymanagement.ext.withIoContext
import javax.inject.Inject

interface LoginRepository {
    suspend fun login(email: String, password: String): RequestResult<Unit>
}

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val taxonomyDataSource: TaxonomyDataSource,
    private val userStorage: UserStorage,
    private val taxonomyStorage: TaxonomyStorage
) : LoginRepository {

    override suspend fun login(email: String, password: String) = withIoContext {
        val loginResponse = loginDataSource.login(LoginRequest(email, password))
            .suspendOnSuccess { withIoContext { userStorage.setUser(it.toDataModel()) } }
            .ignoreValue()

        if (loginResponse !is RequestResult.Success) return@withIoContext loginResponse

        val taxonomyResponse = taxonomyDataSource.getTaxonomies()
            .suspendOnSuccess {
                taxonomyStorage.setPosition(it.positions.map { it.parse() })
                taxonomyStorage.setProjectStatuses(it.projectStatuses.map { it.parse() })
            }
            .ignoreValue()

        return@withIoContext taxonomyResponse
    }

    private fun LoginResponse.toDataModel(): UserDataModel {
        return UserDataModel(
            id, name, email, UserDataModel.AccessLevelDataModel(accessLevel.id, accessLevel.name), token
        )
    }

    private fun TaxonomyResponse.TaxonomyItemResponse.parse() = TaxonomyItem(id.toString(), name)

}