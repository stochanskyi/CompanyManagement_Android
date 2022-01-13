package com.mars.companymanagement.data.database.features.login

import com.mars.companymanagement.data.database.common.realm
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

interface RealmUserDataSource {
    suspend fun getUser(email: String, password: String): UserDTO?
}

class RealmUserDataSourceImpl @Inject constructor(
    private val realmConfig: RealmConfiguration
) : RealmUserDataSource {

    private val database: Realm by realm(realmConfig)

    override suspend fun getUser(email: String, password: String): UserDTO? {
        return database.where(UserDTO::class.java)
            .equalTo("email", email)
            .equalTo("password", password)
            .findFirst()
    }

}