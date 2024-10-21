package com.infy.test.app

import android.app.Application
import com.infy.test.app.io.SpeedLimitExporterFactory

class SpeedLimitApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SpeedLimitExporterFactory().initConnection()
    }

}