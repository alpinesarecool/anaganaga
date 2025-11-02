package com.example.anaganaga.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollographql.apollo3.ApolloClient
import com.example.anaganaga.data.repository.AuthRepository
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anaganaga.presentation.viewmodel.LoginViewModel
import com.example.anaganaga.presentation.viewmodel.LoginViewModelFactory

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val apolloClient = remember {
        ApolloClient.Builder()
            .serverUrl("https://your-backend-url.com/graphql")
            .build()
    }
    val repository = remember { AuthRepository(apolloClient) }
    val factory = remember { LoginViewModelFactory(repository) }
    val viewModel: LoginViewModel = viewModel(factory = factory)

    val loading by viewModel.loading.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val loginErrors by viewModel.loginErrors.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F5EE)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("🕒", fontSize = 48.sp)
            Spacer(Modifier.height(12.dp))
            Text("Time Capsule", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Preserve your memories for tomorrow", fontSize = 14.sp, color = Color.Gray)

            Spacer(Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { viewModel.login(email, password) },
                        enabled = !loading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF92734B)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (loading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Text("Login", color = Color.White)
                        }
                    }

                    // Show login result feedback
                    loginSuccess?.let {
                        if (it) {
                            Text("Login successful!", color = Color.Green)
                        } else if (loginErrors.isEmpty()) {
                            Text("Login failed. Please check your credentials.", color = Color.Red)
                        }
                    }
                    // Show error messages
                    loginErrors.forEach { error ->
                        Text(error, color = Color.Red)
                    }
                }
            }

            Text(
                text = "“Memories are timeless treasures of the heart”",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
