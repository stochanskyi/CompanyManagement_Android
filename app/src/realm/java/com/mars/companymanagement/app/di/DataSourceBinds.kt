package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.database.features.login.RealmUserDataSource
import com.mars.companymanagement.data.database.features.login.RealmUserDataSourceImpl
import com.mars.companymanagement.data.network.auth.LoginDataSource
import com.mars.companymanagement.data.network.auth.LoginDataSourceImpl
import com.mars.companymanagement.data.network.customers.response.CustomersDataSource
import com.mars.companymanagement.data.network.customers.response.CustomersDataSourceImpl
import com.mars.companymanagement.data.network.employees.EmployeesDataSource
import com.mars.companymanagement.data.network.employees.EmployeesDataSourceImpl
import com.mars.companymanagement.data.network.projects.ProjectsDataSource
import com.mars.companymanagement.data.network.projects.ProjectsDataSourceImpl
import com.mars.companymanagement.data.network.taxonomy.TaxonomyDataSource
import com.mars.companymanagement.data.network.taxonomy.TaxonomyDataSourceImpl
import com.mars.companymanagement.data.network.transactions.TransactionsDataSource
import com.mars.companymanagement.data.network.transactions.TransactionsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceBinds {

    @Binds
    fun bindRealmUserDataSource(realmUserDataSource: RealmUserDataSourceImpl): RealmUserDataSource

    @Binds
    fun bindLoginDataSource(loginDataSource: LoginDataSourceImpl): LoginDataSource

    @Binds
    fun bindEmployeesDataSource(employeesDataSource: EmployeesDataSourceImpl): EmployeesDataSource

    @Binds
    fun bindProjectsDataSource(projectsDataSource: ProjectsDataSourceImpl): ProjectsDataSource

    @Binds
    fun bindCustomersDataSource(customersDataSource: CustomersDataSourceImpl): CustomersDataSource

    @Binds
    fun bindTaxonomiesDataStore(taxonomiesDataStore: TaxonomyDataSourceImpl): TaxonomyDataSource

    @Binds
    fun bindTransactionsDataStore(transactionsDataSource: TransactionsDataSourceImpl): TransactionsDataSource
}