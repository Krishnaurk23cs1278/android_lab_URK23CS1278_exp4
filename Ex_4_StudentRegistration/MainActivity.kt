package com.example.progm_4

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen { name, regNo, gender ->
                navController.navigate(
                    "details/${Uri.encode(name)}/${Uri.encode(regNo)}/${Uri.encode(gender)}"
                )
            }
        }

        composable("details/{name}/{registerNumber}/{gender}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val regNo = backStackEntry.arguments?.getString("registerNumber") ?: ""
            val gender = backStackEntry.arguments?.getString("gender") ?: ""

            DetailsScreen(name, regNo, gender) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var registerNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Student Registration", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter Student Name") }
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = registerNumber,
            onValueChange = { registerNumber = it },
            label = { Text("Enter Register Number") }
        )
        Spacer(Modifier.height(16.dp))

        Text("Select Gender")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = gender == "Male", onClick = { gender = "Male" })
            Text("Male")
            Spacer(Modifier.width(16.dp))
            RadioButton(selected = gender == "Female", onClick = { gender = "Female" })
            Text("Female")
        }
        Spacer(Modifier.height(20.dp))

        Button(onClick = { onNavigateToDetails(name, registerNumber, gender) }) {
            Text("Continue")
        }
    }
}

@Composable
fun DetailsScreen(name: String, registerNumber: String, gender: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Student Details", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))
        Text("Name: $name")
        Spacer(Modifier.height(10.dp))
        Text("Register Number: $registerNumber")
        Spacer(Modifier.height(10.dp))
        Text("Gender: $gender")
        Spacer(Modifier.height(20.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}
