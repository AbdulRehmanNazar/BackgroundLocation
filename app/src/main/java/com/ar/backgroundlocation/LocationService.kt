package com.ar.backgroundlocation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat


/**
 * @Author: Abdul Rehman
 * @Date: 06/05/2024.
 */
class LocationService : Service(), LocationUpdatesCallBack {
    private val TAG = LocationService::class.java.simpleName

    private lateinit var gpsLocationClient: GPSLocationClient
    private var notification: NotificationCompat.Builder? = null
    private var notificationManager: NotificationManager? = null

    override fun onCreate() {
        super.onCreate()
        gpsLocationClient = GPSLocationClient()
        gpsLocationClient.setLocationUpdatesCallBack(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_SERVICE_START -> startService()
            ACTION_SERVICE_STOP -> stopService()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        const val ACTION_SERVICE_START = "ACTION_START"
        const val ACTION_SERVICE_STOP = "ACTION_STOP"
    }


    private fun startService() {
        gpsLocationClient.getLocationUpdates(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setContentText("Searching...")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        startForeground(1, notification?.build())
    }

    private fun stopService() {
        gpsLocationClient.setLocationUpdatesCallBack(null)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun locationException(message: String) {
        Log.d(TAG, message)
    }

    override fun onLocationUpdate(location: Location) {
        val updatedNotification = notification?.setContentText(
            "Location: (${location.latitude}, ${location.longitude})"
        )
        notificationManager?.notify(1, updatedNotification?.build())
    }
}