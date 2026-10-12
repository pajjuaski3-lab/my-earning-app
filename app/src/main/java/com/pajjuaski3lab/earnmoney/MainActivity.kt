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

private val AppBlue = Color(0xFF1565C0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(primary = AppBlue)
            ) {
                EarnMoneyApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarnMoneyApp() {
    var selectedPage by remember { mutableStateOf("Home") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Earn Money",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                listOf("Home", "Tasks", "Wallet", "Profile").forEach { page ->
                    NavigationBarItem(
                        selected = selectedPage == page,
                        onClick = { selectedPage = page },
                        icon = {
                            Text(
                                when (page) {
                                    "Home" -> "⌂"
                                    "Tasks" -> "✓"
                                    "Wallet" -> "₹"
                                    else -> "👤"
                                }
                            )
                        },
                        label = { Text(page) }
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Earn Money",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppBlue
                )

                Spacer(Modifier.height(8.dp))
                Text("Your earning journey starts here")
                Spacer(Modifier.height(24.dp))
            }

            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(20.dp)) {
                        Text(
                            text = when (selectedPage) {
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
                            text = when (selectedPage) {
                                "Home" -> "Welcome to Earn Money!"
                                "Tasks" -> "Demo tasks only. Real rewards are not enabled yet."
                                "Wallet" -> "Demo balance: ₹0.00"
                                else -> "Your profile section"
                            }
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
            }

            item {
                Button(
                    onClick = { selectedPage = "Tasks" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Tasks")
                }
            }
        }
    }
}