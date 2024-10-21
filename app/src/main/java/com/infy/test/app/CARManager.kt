package com.infy.test.app

import com.infy.test.app.io.ISpeedLimitExporter
import com.infy.test.app.io.SpeedLimitExporterFactory

/*
* API class
* */

class CARManager {

    private var speedLimitExporter: ISpeedLimitExporter? = null

    init {
        speedLimitExporter = SpeedLimitExporterFactory.getSpeedLimitExporter()
    }

    fun authenticateCarRenter(renterUserName: String?, renterPassword: String?): String {
        // Validate renterUserName and renterPassword.
        // Validation successful call to access car profile
        return getAccessCarProfile()
    }

    private fun getAccessCarProfile(): String {
        return "car_unique_id"
    }

    fun getVehicleSpeedLimit(carId: String?): Int {
        return 80
    }

    fun updateVehicleSpeedLimitExceeded(carId: String) {
        // Call main API of Fleet Company to specific car exceeded speed limit.
        speedLimitExporter?.notifySpeedLimitExceeded(carId)
    }

}