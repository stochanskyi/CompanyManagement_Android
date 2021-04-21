package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.storages.UserStorage
import com.mars.companymanagement.data.storages.UserStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageBindsModule {

    @Binds
    @Singleton
    abstract fun bindUserStorage(storage: UserStorageImpl): UserStorage

}