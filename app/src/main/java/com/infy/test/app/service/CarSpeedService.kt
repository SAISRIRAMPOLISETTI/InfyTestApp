package com.infy.test.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.infy.test.app.ISpeedAidlInterface

/*
* Service for continuously retrieving car speed from the Vehicle CAN bus.
* */

class CarSpeedService : Service() {

    private var carSpeed: Int = 90

    // Binder that binds the service with the activity using AIDL.
    private val speedBinder = object : ISpeedAidlInterface.Stub() {
        // Method called by the client to get the current car speed.
        override fun getCarSpeed(): Int {
            return getCarSpeedFromCAN() // Calls the method to retrieve car speed.
        }
    }

    // This method binds the service to a client and returns the binder instance.
    override fun onBind(intent: Intent?): IBinder {
        return speedBinder
    }

    // Method to retrieve the current car speed.
    private fun getCarSpeedFromCAN(): Int {
        // Implement the logic for integrating with the Car Speed Manager API or AIDL service.
        // This method will be triggered whenever there is an update in the car's speed.
        // Retrieve the car speed from CAN or other relevant sources.
        return carSpeed
    }

}