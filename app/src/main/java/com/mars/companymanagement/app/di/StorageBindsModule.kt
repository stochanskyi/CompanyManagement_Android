package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.storages.taxonomy.TaxonomyStorage
import com.mars.companymanagement.data.storages.taxonomy.TaxonomyStorageImpl
import com.mars.companymanagement.data.storages.user.UserStorage
import com.mars.companymanagement.data.storages.user.UserStorageImpl
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

    @Binds
    @Singleton
    abstract fun bindTaxonomyStorage(storage: TaxonomyStorageImpl): TaxonomyStorage

}