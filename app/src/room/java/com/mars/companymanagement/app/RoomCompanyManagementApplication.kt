package com.mars.companymanagement.app

import android.app.Application
import com.mars.companymanagement.data.database.common.CompanyDatabase
import com.mars.companymanagement.data.database.common.initialization.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class RoomCompanyManagementApplication : Application() {

    @Inject
    lateinit var databaseInitializer: DatabaseInitializer

    @Inject
    lateinit var database: CompanyDatabase

    override fun onCreate() {
        super.onCreate()

        val scope = CoroutineScope(Dispatchers.IO)

        val job = scope.launch {
            databaseInitializer.init(database)
        }

        job.invokeOnCompletion {
            scope.cancel()
        }
    }
}