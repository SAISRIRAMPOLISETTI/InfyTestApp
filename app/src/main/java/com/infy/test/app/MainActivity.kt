package com.infy.test.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.NotificationCompat
import com.infy.test.app.service.CarSpeedService
import com.infy.test.app.simulation.VehicleSpeedModel
import com.infy.test.app.simulation.VehicleSpeedModelListener
import com.infy.test.app.simulation.VehicleSpeedSimulator

class MainActivity : AppCompatActivity(), VehicleSpeedModelListener {

    private var carId: String? = null
    private val tag: String = MainActivity::class.java.getSimpleName()

    private var vehicleSpeedLimit = 80
    private var vehicleSpeed = 90
    // Use MutableLiveData and LiveData variables to get vehicle speed changes from vehicle.

    private var mNotificationManager: NotificationManager? = null
    private var mNotification: Notification? = null
    private val channelId = "InfosysSpeedAlertChannelID"
    private val notificationId = 1234
    private val channelName = "InfosysSpeedAlertNotificationChannelName"

    private var tvCarSpeed: AppCompatTextView? = null

    private var carManager: CARManager? = null

    private var carSpeedService: ISpeedAidlInterface? = null

    private var isBound = false

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            carSpeedService = ISpeedAidlInterface.Stub.asInterface(service)
            isBound = true
            vehicleSpeed = carSpeedService?.carSpeed ?: 0
            validateVehicleSpeed()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            carSpeedService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCarSpeed = findViewById(R.id.tv_car_speed)

        if (carManager == null) carManager = CARManager()

        // 1. Connect/Login to Fleet Company Server.
        // 2. Get current vehicle maximum speed limit[By using Unique ID of CAR] from Fleet.
        // For Step-2 Assumed Current Vehicle Speed Limit to some static value[i.e vehicleSpeedLimit].
        // 3. Get current vehicle speed from CAN/Specific API of our vehicle.
        // For Step-3 Assumed Current Vehicle Speed to local variable[i.e vehicleSpeed].
        // Vehicle Speed keeps changing based on our vehicle speed.

        carId = carManager?.authenticateCarRenter("user_name", "pass_word")

        vehicleSpeedLimit = carId.let {
            carManager?.getVehicleSpeedLimit(it) ?: 0
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        if (mNotification == null) mNotification = createNotification()

        // Starting Car/Vehicle
//        VehicleSpeedSimulator(this).startVehicle()
    }

    override fun onStart() {
        super.onStart()
        Intent(this, CarSpeedService::class.java).also { intent ->
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(mServiceConnection)
            isBound = false
        }
    }

    private fun validateVehicleSpeed() {
        if (vehicleSpeed > vehicleSpeedLimit) {
            // Notifying renter to maintain below specified vehicle speed limit.
            showSpeedAlertWarningToRenter()
            // Notify fleet company to specific car[Using CAR unique ID] crossed speed limit exceeded.
            // Call required API to notify Fleet company.
            carId?.let {
                carManager?.updateVehicleSpeedLimitExceeded(it)
            }
        }
    }

    private fun showSpeedAlertWarningToRenter() {
        mNotificationManager?.notify(notificationId, mNotification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val mNotificationChannel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_HIGH
        )
        mNotificationChannel.apply {
            enableLights(true)
            enableVibration(false)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager?.createNotificationChannel(mNotificationChannel)
    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.speed_alert_title))
            .setContentText(
                String.format(
                    getString(R.string.speed_alert_sub_title),
                    vehicleSpeedLimit.toString()
                )
            )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setTimeoutAfter(5000)
            .build()
    }

    override fun updateVehicleSpeedModel(vehicleSpeedModel: VehicleSpeedModel) {
        Log.d(tag, "updateVehicleSpeedModel()...${vehicleSpeedModel.carSpeed}")
        vehicleSpeed = vehicleSpeedModel.carSpeed
        runOnUiThread {
            tvCarSpeed?.text =
                String.format(getString(R.string.car_speed_text), vehicleSpeedModel.carSpeed)
        }
        validateVehicleSpeed()
    }
}