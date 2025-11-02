package com.example.anaganaga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anaganaga.ui.DashboardScreen
import com.example.anaganaga.ui.LoginScreen
import com.example.anaganaga.ui.SplashScreen
import ui.theme.AnaganagaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnaganagaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") { SplashScreen(navController) }
                    composable("login") { LoginScreen() }
                    composable("DashboardScreen") { DashboardScreen() }
                }
            }
        }
    }
}