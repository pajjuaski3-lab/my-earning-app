
package com.pajjuaski3lab.earnmoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
        setContent { MaterialTheme { EarnMoneyApp() } }
    }
}

@Composable
fun EarnMoneyApp() {
    var tab by remember { mutableStateOf("Home") }
    var demoPoints by remember { mutableIntStateOf(0) }
    var upi by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("100") }
    var message by remember { mutableStateOf("") }

    val tasks = listOf(
        "Complete your profile" to 10,
        "Read app safety tips" to 5,
        "Invite a friend" to 20
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf("Home", "Tasks", "Wallet", "Refer").forEach { item ->
                    NavigationBarItem(
                        selected = tab == item,
                        onClick = { tab = item },
                        icon = {
                            Text(
                                when (item) {
                                    "Home" -> "⌂"
                                    "Tasks" -> "✓"
                                    "Wallet" -> "₹"
                                    else -> "👥"
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Earn Money",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("Tasks • Wallet • Referral")
            }

            item {
                Card {
                    Column(Modifier.padding(18.dp)) {
                        Text("Demo Wallet Points")
                        Text(
                            "$demoPoints points",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("ಇವು ನಿಜವಾದ ಹಣವಲ್ಲ.")
                    }
                }
            }

            when (tab) {
                "Home" -> {
                    item {
                        Text("Welcome!", fontSize = 22.sp)
                        Text("Tasks ಪೂರ್ಣಗೊಳಿಸಿ demo points ಗಳಿಸಿ.")
                    }
                    item { DemoBannerAd() }
                    items(tasks) { task ->
                        TaskCard(task.first, task.second) {
                            demoPoints += task.second
                            message = "Demo points ಸೇರಿಸಲಾಗಿದೆ."
                        }
                    }
                }

                "Tasks" -> {
                    item { Text("Available Tasks", fontSize = 22.sp) }
                    items(tasks) { task ->
                        TaskCard(task.first, task.second) {
                            demoPoints += task.second
                            message = "Demo points ಸೇರಿಸಲಾಗಿದೆ."
                        }
                    }
                }

                "Wallet" -> {
                    item { Text("Withdrawal Request", fontSize = 22.sp) }
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
                            label = { Text("Your UPI ID") },
                            placeholder = { Text("name@bank") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        Button(
                            onClick = {
                                message =
                                    if (upi.contains("@") &&
                                        (amount.toIntOrNull() ?: 0) > 0
                                    ) {
                                        "Demo ಮಾತ್ರ: request backend ಗೆ ಕಳುಹಿಸಿಲ್ಲ."
                                    } else {
                                        "ಸರಿಯಾದ ಮೊತ್ತ ಮತ್ತು UPI ID ನಮೂದಿಸಿ."
                                    }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Request Withdrawal")
                        }
                    }
                    item {
                        Text(
                            "ಇದು UI demo. ನಿಜವಾದ withdrawal ಅಥವಾ UPI payment ನಡೆಯುವುದಿಲ್ಲ."
                        )
                    }
                }

                "Refer" -> {
                    item { Text("Refer & Earn", fontSize = 22.sp) }
                    item {
                        Text("ನಿಮ್ಮ referral code: DEMO123")
                        Text("ಈ code demo ಮಾತ್ರ; referral backend ಇನ್ನೂ ಸೇರಿಸಿಲ್ಲ.")
                        Button(
                            onClick = {
                                message = "ನಿಮ್ಮ referral code: DEMO123"
                            }
                        ) { Text("Show Referral Code") }
                    }
                }
            }

            if (message.isNotBlank()) {
                item { Text(message, color = MaterialTheme.colorScheme.primary) }
            }
        }
    }
}

@Composable
fun TaskCard(
    title: String,
    points: Int,
    onComplete: () -> Unit
) {
    var completed by remember { mutableStateOf(false) }

    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Medium)
                Text("$points demo points")
            }
            Button(
                enabled = !completed,
                onClick = {
                    completed = true
                    onComplete()
                }
            ) {
                Text(if (completed) "Done" else "Start")
            }
        }
    }
}

@Composable
fun DemoBannerAd() {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        factory = {
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/9214589741"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
