package com.ar.backgroundlocation

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 * @Author: Abdul Rehman
 * @Date: 06/05/2024.
 */
@Preview
@Composable
fun App() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            //Start Service
            Toast.makeText(context, "Service Start button clicked", Toast.LENGTH_SHORT).show()
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_SERVICE_START
                context.startService(this)
            }
        }) {
            Text(text = "Start Service")
        }
        Spacer(modifier = Modifier.padding(12.dp))
        Button(onClick = {
            //Stop Service
            Toast.makeText(context, "Service Stop button clicked", Toast.LENGTH_SHORT).show()
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_SERVICE_STOP
                context.startService(this)
            }
        }) {
            Text(text = "Stop Service")

        }
    }
}