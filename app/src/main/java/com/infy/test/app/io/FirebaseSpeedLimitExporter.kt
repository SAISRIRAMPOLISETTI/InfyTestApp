package com.infy.test.app.io

class FirebaseSpeedLimitExporter : ISpeedLimitExporter {

    //Initialize firebase data base.

    override fun notifySpeedLimitExceeded(carId: String?) {
        // Call fire base API to notify specific car Id speed limit exceeded.
    }
}