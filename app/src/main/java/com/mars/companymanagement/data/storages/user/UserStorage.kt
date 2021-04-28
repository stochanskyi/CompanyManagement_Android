package com.mars.companymanagement.data.storages.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

interface UserStorage {

    val userDataFlow: Flow<UserDataModel?>

    fun getUser(): UserDataModel?

    suspend fun setUser(user: UserDataModel)

    suspend fun clearUser()
}

class UserStorageImpl @Inject constructor() : UserStorage {

    private var user: UserDataModel? = null

    override val userDataFlow: MutableSharedFlow<UserDataModel?> = MutableSharedFlow()

    override fun getUser(): UserDataModel? {
        return user
    }

    override suspend fun setUser(user: UserDataModel) {
        this.user = user
        userDataFlow.emit(user)
    }

    override suspend fun clearUser() {
        this.user = null
        userDataFlow.emit(null)
    }

}