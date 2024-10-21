package com.infy.test.app.io

class SpeedLimitExporterFactory {

    companion object {
        private var speedLimitExporter: ISpeedLimitExporter? = null

        fun getSpeedLimitExporter(): ISpeedLimitExporter {
            if (speedLimitExporter == null) {
                speedLimitExporter = FirebaseSpeedLimitExporter()
//                speedLimitExporter = AWSSpeedLimitExporter()
            }
            return speedLimitExporter as ISpeedLimitExporter
        }
    }

    fun initConnection() {
        getSpeedLimitExporter()
    }

}