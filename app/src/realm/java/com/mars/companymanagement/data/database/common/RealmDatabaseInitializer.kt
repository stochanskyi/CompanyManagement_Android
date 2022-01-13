package com.mars.companymanagement.data.database.common

import com.mars.companymanagement.data.database.features.login.AccessLevelDTO
import com.mars.companymanagement.data.database.features.login.UserDTO
import com.mars.companymanagement.data.repositories.user.models.levels.base.AccessLevel
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait

interface RealmDatabaseInitializer {

    suspend fun init(database: Realm)

}


/**
 * Initializes database with default data
 * Should be replaced with actions
 */
class RealmDatabaseInitializerImpl : RealmDatabaseInitializer {

    override suspend fun init(database: Realm) {
        val user = UserDTO().apply {
            id = 1
            username = "User"
            email = "test@t.t"
            password = "t"
            accessLevel = AccessLevelDTO().apply {
                id = 1
                name = "Owner"
            }
        }

        database.executeTransactionAwait {
            it.deleteAll()
            it.insert(user)
        }
    }

}