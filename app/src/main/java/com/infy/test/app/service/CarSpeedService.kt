package com.infy.test.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.infy.test.app.ISpeedAidlInterface

/*
* It will get Car speed continuously from CAN.
* */

class CarSpeedService : Service() {

    private val speedBinder = object : ISpeedAidlInterface.Stub() {
        override fun getCarSpeed(): Int {
            return getCarSpeedFromCAN()
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return speedBinder
    }


    private fun getCarSpeedFromCAN(): Int {
        // Fetching car speed from CAN or etc.
        return 60
    }

}