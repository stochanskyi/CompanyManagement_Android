package com.mars.companymanagement.app.di

import android.content.Context
import com.mars.companymanagement.data.database.common.CompanyDatabase
import com.mars.companymanagement.data.database.common.initialization.DatabaseInitializer
import com.mars.companymanagement.data.database.common.initialization.DatabaseInitializerImpl
import com.mars.companymanagement.data.database.feature.employees.EmployeeDao
import com.mars.companymanagement.data.database.feature.taxonomy.positions.PositionDao
import com.mars.companymanagement.data.database.feature.taxonomy.project.ProjectStatusDao
import com.mars.companymanagement.data.database.feature.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun getDatabaseInitializer(): DatabaseInitializer = DatabaseInitializerImpl()

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): CompanyDatabase {
        return CompanyDatabase.create(context)
    }

    @Provides
    fun getUserDao(database: CompanyDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun getPositionDao(database: CompanyDatabase): PositionDao {
        return database.positionsDao()
    }


    @Provides
    fun getProjectStatusDao(database: CompanyDatabase): ProjectStatusDao {
        return database.projectStatusDao()
    }

    @Provides
    fun getEmployeeDao(database: CompanyDatabase): EmployeeDao {
        return database.employeeDao()
    }

}