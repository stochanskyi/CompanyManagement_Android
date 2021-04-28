package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.common.StringProvider
import com.mars.companymanagement.data.common.StringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindsModule {

    @Binds
    @Singleton
    abstract fun bindStringProvider(stringProvider: StringProviderImpl): StringProvider
}