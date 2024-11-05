package com.infy.test.app.io

// Firebase API class to communicate with fleet company.
// Firebase implementation of ISpeedLimitExporter.
class FirebaseSpeedLimitExporter : ISpeedLimitExporter {

    // Initialize Firebase database
    init {
        // Firebase initialization logic
    }

    override fun notifySpeedLimitExceeded(carId: String?) {
        // Call fire base API to notify specific car Id speed limit exceeded.
    }
}