package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.network.auth.AuthApi
import com.mars.companymanagement.data.network.customers.CustomersApi
import com.mars.companymanagement.data.network.employees.EmployeesApi
import com.mars.companymanagement.data.network.projects.ProjectsApi
import com.mars.companymanagement.data.network.taxonomy.TaxonomyApi
import com.mars.companymanagement.data.network.transactions.TransactionsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideEmployeesApi(retrofit: Retrofit): EmployeesApi {
        return retrofit.create(EmployeesApi::class.java)
    }

    @Provides
    fun provideProjectsApi(retrofit: Retrofit): ProjectsApi {
        return retrofit.create(ProjectsApi::class.java)
    }

    @Provides
    fun provideCustomersApi(retrofit: Retrofit): CustomersApi {
        return retrofit.create(CustomersApi::class.java)
    }

    @Provides
    fun provideTaxonomyApi(retrofit: Retrofit): TaxonomyApi {
        return retrofit.create(TaxonomyApi::class.java)
    }

    @Provides
    fun provideTransactionsApi(retrofit: Retrofit): TransactionsApi {
        return retrofit.create(TransactionsApi::class.java)
    }

}