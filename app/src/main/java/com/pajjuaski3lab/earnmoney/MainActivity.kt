
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                EarnMoneyApp()
            }
        }
    }
}

@Composable
fun EarnMoneyApp() {
    var page by remember { mutableStateOf("Home") }
    var points by remember { mutableIntStateOf(0) }
    var amount by remember { mutableStateOf("100") }
    var upi by remember { mutableStateOf("") }
    var referral by remember { mutableStateOf("PAJJU123") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Earn Money") }
            )
        },
        bottomBar = {
            NavigationBar {
                listOf("Home", "Tasks", "Wallet", "Refer").forEach {
                    NavigationBarItem(
                        selected = page == it,
                        onClick = { page = it },
                        icon = {
                            Text(
                                when (it) {
                                    "Home" -> "⌂"
                                    "Tasks" -> "✓"
                                    "Wallet" -> "₹"
                                    else -> "👥"
                                }
                            )
                        },
                        label = { Text(it) }
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card {
                    Column(Modifier.padding(20.dp)) {
                        Text("ನಿಮ್ಮ Demo Wallet")
                        Text(
                            "$points Points",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("ಇವು ನಿಜವಾದ ಹಣವಲ್ಲ.")
                    }
                }
            }

            when (page) {
                "Home" -> {
                    item {
                        Text("Welcome!", fontSize = 24.sp)
                        Text("Tasks ಪೂರ್ಣಗೊಳಿಸಿ demo points ಪಡೆಯಿರಿ.")
                        Text("ಜಾಹೀರಾತುಗಳನ್ನು ಇಲ್ಲಿ ನಂತರ ಸೇರಿಸಬಹುದು.")
                    }
                    item {
                        Button(
                            onClick = { page = "Tasks" },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Start Earning")
                        }
                    }
                }

                "Tasks" -> {
                    item {
                        Text("Available Tasks", fontSize = 22.sp)
                    }
                    item {
                        TaskItem("Complete profile", 10) {
                            points += 10
                            message = "10 demo points ಸೇರಿಸಲಾಗಿದೆ."
                        }
                    }
                    item {
                        TaskItem("Read safety tips", 5) {
                            points += 5
                            message = "5 demo points ಸೇರಿಸಲಾಗಿದೆ."
                        }
                    }
                    item {
                        TaskItem("Daily bonus demo", 2) {
                            points += 2
                            message = "2 demo points ಸೇರಿಸಲಾಗಿದೆ."
                        }
                    }
                }

                "Wallet" -> {
                    item {
                        Text("Withdrawal Request", fontSize = 22.sp)
                    }
                    item {
                        OutlinedTextField(
                            value = amount,
                            onValueChange = { amount = it },
                            label = { Text("Amount in ₹") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = upi,
                            onValueChange = { upi = it },
                            label = { Text("UPI ID") },
                            placeholder = { Text("name@bank") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        Button(
                            onClick = {
                                val value = amount.toIntOrNull()
                                message = if (
                                    value != null &&
                                    value > 0 &&
                                    upi.contains("@")
                                ) {
                                    "Demo request ಮಾತ್ರ. ಹಣ ಕಳುಹಿಸಿಲ್ಲ."
                                } else {
                                    "ಮೊತ್ತ ಮತ್ತು UPI ID ಪರಿಶೀಲಿಸಿ."
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Request Withdrawal")
                        }
                    }
                    item {
                        Text(
                            "ಈ ಆವೃತ್ತಿಯಲ್ಲಿ ನಿಜವಾದ UPI ಪಾವತಿ ಇಲ್ಲ."
                        )
                    }
                }

                "Refer" -> {
                    item {
                        Text("Refer & Earn", fontSize = 22.sp)
                        Text("Demo Referral Code: $referral")
                    }
                    item {
                        Button(
                            onClick = {
                                message =
                                    "Referral code: $referral"
                            }
                        ) {
                            Text("Show Referral Code")
                        }
                    }
                }
            }

            if (message.isNotBlank()) {
                item {
                    Text(
                        message,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    title: String,
    reward: Int,
    onClick: () -> Unit
) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Medium)
                Text("$reward demo points")
            }
            Button(onClick = onClick) {
                Text("Complete")
            }
        }
    }
}
