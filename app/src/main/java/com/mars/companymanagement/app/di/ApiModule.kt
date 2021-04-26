package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.network.auth.AuthApi
import com.mars.companymanagement.data.network.employees.EmployeesApi
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

}