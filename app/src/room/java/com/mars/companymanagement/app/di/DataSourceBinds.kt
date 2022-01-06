package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionsDataSource
import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionsDataSourceImpl
import com.mars.companymanagement.data.database.feature.taxonomy.project.ProjectStatusesDataSource
import com.mars.companymanagement.data.database.feature.taxonomy.project.ProjectStatusesDataSourceImpl
import com.mars.companymanagement.data.database.feature.user.UserDataSource
import com.mars.companymanagement.data.database.feature.user.UserDataSourceImpl
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
abstract class DataSourceBinds {

    @Binds
    abstract fun bindUserDataSource(userDataSource: UserDataSourceImpl): UserDataSource

    @Binds
    abstract fun bindPositionsDataSource(userDataSource: PositionsDataSourceImpl): PositionsDataSource

    @Binds
    abstract fun bindProjectStatusesDataSource(projectStatusesDataSource: ProjectStatusesDataSourceImpl): ProjectStatusesDataSource

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

    @Binds
    abstract fun bindTransactionsDataStore(transactionsDataSource: TransactionsDataSourceImpl): TransactionsDataSource
}