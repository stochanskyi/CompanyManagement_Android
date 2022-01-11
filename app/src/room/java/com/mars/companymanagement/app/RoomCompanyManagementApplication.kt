package com.mars.companymanagement.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoomCompanyManagementApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //TODO init database
    }
}