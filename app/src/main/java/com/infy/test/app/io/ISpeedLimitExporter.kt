package com.infy.test.app.io

interface ISpeedLimitExporter {
    fun notifySpeedLimitExceeded(carId: String?)
}