package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.repositories.login.LoginRepository
import com.mars.companymanagement.data.repositories.login.LoginRepositoryImpl
import com.mars.companymanagement.data.repositories.customers.CustomersRepository
import com.mars.companymanagement.data.repositories.customers.CustomersRepositoryImpl
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.EmployeesRepositoryImpl
import com.mars.companymanagement.data.repositories.projects.ProjectsRepository
import com.mars.companymanagement.data.repositories.projects.ProjectsRepositoryImpl
import com.mars.companymanagement.data.repositories.taxonomies.TaxonomyRepository
import com.mars.companymanagement.data.repositories.taxonomies.TaxonomyRepositoryImpl
import com.mars.companymanagement.data.repositories.user.UserRepository
import com.mars.companymanagement.data.repositories.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindsModule {

    @Binds
    abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindEmployeesRepository(employeesRepository: EmployeesRepositoryImpl): EmployeesRepository

    @Binds
    @Singleton
    abstract fun bindProjectsRepository(projectsRepository: ProjectsRepositoryImpl): ProjectsRepository

    @Binds
    @Singleton
    abstract fun bindCustomersRepository(customersRepository: CustomersRepositoryImpl): CustomersRepository

    @Binds
    abstract fun bindTaxonomyRepository(taxonomyRepository: TaxonomyRepositoryImpl): TaxonomyRepository
}