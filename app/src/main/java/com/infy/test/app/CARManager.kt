package com.infy.test.app

/*
* API class
* */

class CARManager {

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

    fun updateVehicleSpeedLimitExceeded() {
        // Call main API of Fleet Company.
    }

}