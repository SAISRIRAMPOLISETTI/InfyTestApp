package com.infy.test.app.io

// SpeedLimitExporterFactory class is responsible for managing communication channels.
class SpeedLimitExporterFactory(private var iSpeedLimitExporter: ISpeedLimitExporter?) {

    // Method to update the communication channel to a new instance of ISpeedLimitExporter
    fun setSpeedLimitCommunicationChannel(channel: ISpeedLimitExporter) {
        iSpeedLimitExporter = channel
    }

    // Method to notify the communication channel when a car exceeds the speed limit
    fun updateFleetAPIForSpeedLimitExceeds(carID: String?) {
        iSpeedLimitExporter?.notifySpeedLimitExceeded(carID)
    }

}