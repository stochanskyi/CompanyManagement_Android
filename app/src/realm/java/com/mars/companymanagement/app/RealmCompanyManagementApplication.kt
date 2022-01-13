package com.mars.companymanagement.app

import android.app.Application
import com.mars.companymanagement.data.database.common.RealmDatabaseInitializer
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltAndroidApp
class RealmCompanyManagementApplication : Application() {

    @Inject
    lateinit var databaseInitializer: RealmDatabaseInitializer

    @Inject
    lateinit var database: RealmConfiguration

    override fun onCreate() {

        Realm.init(this@RealmCompanyManagementApplication)

        super.onCreate()

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch(Dispatchers.IO) {
            val db = Realm.getInstance(database)
            databaseInitializer.init(db)
        }

    }

}