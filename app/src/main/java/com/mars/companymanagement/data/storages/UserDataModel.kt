package com.mars.companymanagement.data.storages

import com.mars.companymanagement.data.repositories.user.models.levels.AdminAccessLevel
import com.mars.companymanagement.data.repositories.user.models.levels.BookkeeperAccessLevel
import com.mars.companymanagement.data.repositories.user.models.levels.OwnerAccessLevel
import com.mars.companymanagement.data.repositories.user.models.levels.base.AccessLevel
import com.mars.companymanagement.data.repositories.user.models.levels.base.AccessLevelConstants

data class UserDataModel(
    val id: String,
    val name: String,
    val email: String,
    val accessLevel: AccessLevelDataModel,
    val token: String
) {
    data class AccessLevelDataModel(
        val id: String,
        val name: String
    ) {
        fun generateAccessLevel(): AccessLevel? {
            return when (id) {
                AccessLevelConstants.OWNER_LEVEL_CODE -> OwnerAccessLevel()
                AccessLevelConstants.ADMIN_LEVEL_CODE -> AdminAccessLevel()
                AccessLevelConstants.BOOKKEEPER_LEVEL_CODE -> BookkeeperAccessLevel()
                else -> null
            }
        }
    }
}