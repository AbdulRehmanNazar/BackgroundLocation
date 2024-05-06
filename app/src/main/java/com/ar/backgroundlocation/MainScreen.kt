package com.ar.backgroundlocation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview


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
        }) {
            Text(text = "Start Service")
        }
        Button(onClick = {
            //Stop Service
            Toast.makeText(context, "Service Stop button clicked", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Stop Service")
        }
    }
}