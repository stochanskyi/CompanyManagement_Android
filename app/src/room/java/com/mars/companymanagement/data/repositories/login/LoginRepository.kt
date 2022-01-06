package com.mars.companymanagement.data.repositories.login

import com.mars.companymanagement.data.common.RequestResult
import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionEntity
import com.mars.companymanagement.data.database.feature.taxonomy.project.ProjectStatusEntity
import com.mars.companymanagement.data.database.feature.user.UserDataSource
import com.mars.companymanagement.data.database.feature.user.entities.UserWithAccessLevelEntity
import com.mars.companymanagement.data.network.taxonomy.models.TaxonomyResponse
import com.mars.companymanagement.data.storages.taxonomy.model.TaxonomyItem
import com.mars.companymanagement.data.storages.user.UserDataModel
import com.mars.companymanagement.data.storages.user.UserStorage
import com.mars.companymanagement.ext.withIoContext
import javax.inject.Inject

class RoomLoginRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userStorage: UserStorage,
) : LoginRepository {

    override suspend fun login(email: String, password: String) = withIoContext {
        userDataSource.getUserWithAccessLevelByCredentials(email, password)
            ?.let { userStorage.setUser(it.toDataModel()) }
            ?: return@withIoContext RequestResult.UnknownError()


        return@withIoContext RequestResult.Success(Unit)
    }


    private fun UserWithAccessLevelEntity.toDataModel(): UserDataModel {
        return UserDataModel(
            user.userId.toString(),
            user.userName,
            user.email,
            UserDataModel.AccessLevelDataModel(accessLevel.levelId.toString(), accessLevel.levelName),
            ""
        )
    }

    private fun TaxonomyResponse.TaxonomyItemResponse.parse() = TaxonomyItem(id.toString(), name)

}