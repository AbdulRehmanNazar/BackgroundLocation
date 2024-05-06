package com.ar.backgroundlocation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.ar.backgroundlocation.ui.theme.BackGroundLocationTheme

class MainActivity : ComponentActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackGroundLocationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestMultiplePermissions.launch(
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            } else {
                checkLocationPerm()
            }
        } else {
            checkLocationPerm()
        }
    }

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            Log.d("DEBUG", "${it.key} = ${it.value}")
            if (it.key == "android.permission.POST_NOTIFICATIONS" && it.value) {
                askForBGPermission()
            }
        }
    }

    private val requestLocationPerm = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            askForBGPermission()
        } else {
            Log.d(TAG, "Permission is not $isGranted")
        }
    }

    private val requestBGLocationPerm = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "Background Permission is $isGranted")
        } else {
            Log.d(TAG, "Background Permission is not $isGranted")
        }
    }

    private fun checkLocationPerm() {
        if (!checkLocationPermissions()) {
            requestLocationPerm.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            askForBGPermission()
        }
    }

    private fun askForBGPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestBGLocationPerm.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    private fun checkLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}






