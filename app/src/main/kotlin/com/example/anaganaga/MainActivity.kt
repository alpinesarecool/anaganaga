package com.example.anaganaga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ui.MainScreen
import ui.theme.AnaganagaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnaganagaTheme {
                MainScreen()
            }
        }
    }
}