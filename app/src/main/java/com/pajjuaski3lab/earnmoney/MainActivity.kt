package com.pajjuaski3lab.earnmoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AppBlue = Color(0xFF1565C0)
private val BackgroundBlue = Color(0xFFF0F6FF)

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
    Scaffold(
        containerColor = BackgroundBlue,
        topBar = {
            Surface(color = AppBlue) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Earn Money",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Tasks • Rewards • Wallet",
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppBlue
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {
                        Text(
                            text = "My Wallet",
                            color = Color.White,
                            fontSize = 18.sp
                        )

                        Text(
                            text = "₹0.00",
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Demo balance",
                            color = Color.White
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Earn Rewards",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                FeatureCard(
                    title = "Daily Check-in",
                    description = "Explore daily rewards",
                    buttonText = "Check In"
                )
            }

            item {
                FeatureCard(
                    title = "Complete Tasks",
                    description = "Discover available tasks",
                    buttonText = "View Tasks"
                )
            }

            item {
                FeatureCard(
                    title = "Spin & Win",
                    description = "Spin game demo",
                    buttonText = "Play Game"
                )
            }

            item {
                FeatureCard(
                    title = "Refer & Earn",
                    description = "Invite friends to the app",
                    buttonText = "Refer Friends"
                )
            }

            item {
                FeatureCard(
                    title = "Withdraw Money",
                    description = "Withdrawal feature demo",
                    buttonText = "Withdraw"
                )
            }

            item {
                Text(
                    text = "Demo only: rewards and withdrawals " +
                        "are not connected to real money.",
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun FeatureCard(
    title: String,
    description: String,
    buttonText: String
) {
    val clicked = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                color = AppBlue,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = description)

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    clicked.value = !clicked.value
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppBlue
                )
            ) {
                Text(buttonText)
            }

            if (clicked.value) {
                Text(
                    text = "This feature is coming soon.",
                    modifier = Modifier.padding(top = 8.dp),
                    color = AppBlue
                )
            }
        }
    }
}