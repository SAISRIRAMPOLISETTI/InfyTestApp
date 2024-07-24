package com.infy.test.app.simulation

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.infy.test.app.MainActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

class VehicleSpeedSimulator(private val context: MainActivity) {

    private val tag: String = VehicleSpeedSimulator::class.java.getSimpleName()
    private var executor: ScheduledExecutorService? = null
    private var carSpeedSimulationList: ArrayList<VehicleSpeedModel>? = null

    fun startVehicle() {
        executor = Executors.newScheduledThreadPool(1)

        CompletableFuture.runAsync({
            if (!readJsonFromFile()) {
                return@runAsync
            }

            for (simulation in carSpeedSimulationList ?: ArrayList()) {
                executor?.schedule(
                    CANPublisherJob(simulation, context), simulation.timeDelay,
                    TimeUnit.MILLISECONDS
                )
            }

            val lastSimulationDelay: Long? =
                carSpeedSimulationList?.get(carSpeedSimulationList?.size?.minus(1) ?: 0)?.timeDelay

            executor?.schedule({
                executor?.shutdown()
            }, lastSimulationDelay ?: 0, TimeUnit.MILLISECONDS)
        }, executor)
    }

    private fun readJsonFromFile(): Boolean {
        if (carSpeedSimulationList.isNullOrEmpty()) {
            var inputStream: InputStream? = null
            val json: String
            try {
                inputStream = context.assets.open("CarSpeedData.json")
                json = BufferedReader(InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"))
                val listType: Type = object : TypeToken<ArrayList<VehicleSpeedModel?>?>() {}.type
                carSpeedSimulationList =
                    Gson().fromJson<ArrayList<VehicleSpeedModel>>(json, listType)
            } catch (e: Exception) {
                Log.e(tag, tag + " Error reading readJsonFromFile() >> " + e.message)
                try {
                    inputStream?.close()
                } catch (ex: IOException) {
                    Log.e(tag, "Error Input Stream close >> " + ex.message)
                }
                return false
            }
        }
        return true
    }

    internal class CANPublisherJob(
        private var vehicleSpeedModel: VehicleSpeedModel,
        private var context: MainActivity
    ) : Runnable {

        override fun run() {
            context.updateVehicleSpeedModel(vehicleSpeedModel)
        }
    }

}