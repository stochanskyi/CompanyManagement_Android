package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.repositories.LoginRepository
import com.mars.companymanagement.data.repositories.LoginRepositoryImpl
import com.mars.companymanagement.data.repositories.user.UserRepository
import com.mars.companymanagement.data.repositories.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindsModule {

    @Binds
    abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}