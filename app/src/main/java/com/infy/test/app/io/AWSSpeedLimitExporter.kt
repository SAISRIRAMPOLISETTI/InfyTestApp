package com.infy.test.app.io

class AWSSpeedLimitExporter : ISpeedLimitExporter {

    // Initialize AWS API.

    override fun notifySpeedLimitExceeded(carId: String?) {
        // Call AWS API to notify specific car Id speed limit exceeded.
    }
}