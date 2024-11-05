package com.infy.test.app

import com.infy.test.app.io.AWSSpeedLimitExporter
import com.infy.test.app.io.FirebaseSpeedLimitExporter
import com.infy.test.app.io.SpeedLimitExporterFactory

/*
* API class
* */

class CARManager {

    private var carSpeedLimit: Int = 100
    private var speedLimitExporter: SpeedLimitExporterFactory? = null

    init {
        // Initializes the default communication channel to Firebase.
        speedLimitExporter = SpeedLimitExporterFactory(FirebaseSpeedLimitExporter())
    }

    // Method to change the communication channel from Firebase to AWS or other services.
    fun switchToAWSChannel() {
        speedLimitExporter?.setSpeedLimitCommunicationChannel(AWSSpeedLimitExporter())
    }

    // Method to authenticate the renter's username and password provided by the fleet company.
    fun authenticateCarRenter(renterUserName: String?, renterPassword: String?): String {
        // Validates the renter's username and password.
        // If validation is successful, calls to access the car profile.
        return getAccessCarProfile()
    }

    // Method to access the renter's car profile.
    private fun getAccessCarProfile(): String {
        // Calls the Fleet/Car API to retrieve the car profile.
        return "car_unique_id"
    }

    // Method to retrieve the speed limit set by the fleet company for a specific vehicle.
    fun getVehicleSpeedLimit(carId: String?): Int {
        // Calls the Fleet API to retrieve the configured car speed limit.
        return carSpeedLimit
    }

    // Method to update the fleet API when a vehicle exceeds the speed limit.
    fun updateVehicleSpeedLimitExceeded(carId: String) {
        // Calls the fleet company's main API to notify that a specific car has exceeded the speed limit.
        speedLimitExporter?.updateFleetAPIForSpeedLimitExceeds(carId)
    }

}