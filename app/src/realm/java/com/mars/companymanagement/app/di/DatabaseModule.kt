package com.mars.companymanagement.app.di

import com.mars.companymanagement.data.database.common.RealmDatabaseInitializer
import com.mars.companymanagement.data.database.common.RealmDatabaseInitializerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun getRealmConfig(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name("Company Management Database")
            .build()
    }

    @Provides
    fun getRealmDatabase(config: RealmConfiguration): Realm {
        return Realm.getInstance(config)
    }

    @Provides
    fun getRealmDatabaseInitializer(): RealmDatabaseInitializer {
        return RealmDatabaseInitializerImpl()
    }

}