package com.infy.test.app.io

// Common interface for Firebase/AWS communication channel.
// Define the ISpeedLimitExporter interface.
interface ISpeedLimitExporter {
    fun notifySpeedLimitExceeded(carId: String?)
}