
package com.pajjuaski3lab.earnmoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Blue = Color(0xFF1565C0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Blue
                )
            ) {
                EarnMoneyApp()
            }
        }
    }
}

@Composable
fun EarnMoneyApp() {
    var page by remember { mutableStateOf("Home") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Earn Money") }
            )
        },
        bottomBar = {
            NavigationBar {
                listOf("Home", "Tasks", "Wallet", "Profile")
                    .forEach { item ->
                        NavigationBarItem(
                            selected = page == item,
                            onClick = { page = item },
                            icon = {
                                Text(
                                    when (item) {
                                        "Home" -> "⌂"
                                        "Tasks" -> "✓"
                                        "Wallet" -> "₹"
                                        else -> "👤"
                                    }
                                )
                            },
                            label = { Text(item) }
                        )
                    }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Earn Money",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue
                )
                Spacer(Modifier.height(8.dp))
                Text("Your earning journey starts here")
                Spacer(Modifier.height(24.dp))
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            when (page) {
                                "Home" -> "Welcome!"
                                "Tasks" -> "Available Tasks"
                                "Wallet" -> "My Wallet"
                                else -> "My Profile"
                            },
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            when (page) {
                                "Home" ->
                                    "Your app is being developed."
                                "Tasks" ->
                                    "Demo tasks only. Rewards are not enabled."
                                "Wallet" ->
                                    "Demo balance: ₹0.00. No real money is stored."
                                else ->
                                    "Login and account settings will be added later."
                            }
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            item {
                Button(
                    onClick = { page = "Tasks" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Tasks")
                }
            }
        }
    }
}
