package com.infy.test.app.io

// AWS API class to communicate with fleet company.
// AWS implementation of ISpeedLimitExporter
class AWSSpeedLimitExporter : ISpeedLimitExporter {

    // Initialize AWS API
    init {
        // AWS initialization logic
    }

    override fun notifySpeedLimitExceeded(carId: String?) {
        // Call AWS API to notify specific car Id speed limit exceeded.
    }
}