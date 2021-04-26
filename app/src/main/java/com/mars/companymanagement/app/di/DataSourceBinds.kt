package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.network.auth.LoginDataSource
import com.mars.companymanagement.data.network.auth.LoginDataSourceImpl
import com.mars.companymanagement.data.network.employees.EmployeesDataSource
import com.mars.companymanagement.data.network.employees.EmployeesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceBinds {

    @Binds
    abstract fun bindLoginDataSource(loginDataSource: LoginDataSourceImpl): LoginDataSource

    @Binds
    abstract fun bindEmployeesDataSource(employeesDataSource: EmployeesDataSourceImpl): EmployeesDataSource

}