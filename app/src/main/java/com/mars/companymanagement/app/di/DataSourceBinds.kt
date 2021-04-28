package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.network.auth.LoginDataSource
import com.mars.companymanagement.data.network.auth.LoginDataSourceImpl
import com.mars.companymanagement.data.network.customers.models.CustomersDataSource
import com.mars.companymanagement.data.network.customers.models.CustomersDataSourceImpl
import com.mars.companymanagement.data.network.employees.EmployeesDataSource
import com.mars.companymanagement.data.network.employees.EmployeesDataSourceImpl
import com.mars.companymanagement.data.network.projects.ProjectsDataSource
import com.mars.companymanagement.data.network.projects.ProjectsDataSourceImpl
import com.mars.companymanagement.data.network.taxonomy.TaxonomyDataSource
import com.mars.companymanagement.data.network.taxonomy.TaxonomyDataSourceImpl
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

    @Binds
    abstract fun bindProjectsDataSource(projectsDataSource: ProjectsDataSourceImpl): ProjectsDataSource

    @Binds
    abstract fun bindCustomersDataSource(customersDataSource: CustomersDataSourceImpl): CustomersDataSource

    @Binds
    abstract fun bindTaxonomiesDataStore(taxonomiesDataStore: TaxonomyDataSourceImpl): TaxonomyDataSource
}