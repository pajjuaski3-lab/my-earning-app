package com.pajjuaski3lab.earnmoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Blue = Color(0xFF1565C0)
private val LightBlue = Color(0xFFEAF3FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                EarnMoneyScreen()
            }
        }
    }
}

@Composable
fun EarnMoneyScreen() {
    Scaffold(
        containerColor = LightBlue,
        topBar = {
            Surface(color = Blue) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Earn Money",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Complete tasks and earn rewards",
                        color = Color.White
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Blue
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                RewardCard(
                    title = "Daily Check-in",
                    description = "Check in to explore daily rewards",
                    buttonText = "Check In"
                )
            }

            item {
                RewardCard(
                    title = "Complete Tasks",
                    description = "Explore available earning tasks",
                    buttonText = "View Tasks"
                )
            }

            item {
                RewardCard(
                    title = "Refer & Earn",
                    description = "Invite friends to join the app",
                    buttonText = "Refer Friends"
                )
            }

            item {
                RewardCard(
                    title = "Withdraw",
                    description = "Submit a withdrawal request when eligible",
                    buttonText = "Withdrawal"
                )
            }

            item {
                Text(
                    text = "Note: This is a demo interface. No real money is credited or paid."
                )
            }
        }
    }
}

@Composable
fun RewardCard(
    title: String,
    description: String,
    buttonText: String
) {
    var clicked by androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf(false)
    }

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
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Blue
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = description)

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { clicked = !clicked },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )
            ) {
                Text(buttonText)
            }

            if (clicked) {
                Text(
                    text = "Feature coming soon!",
                    color = Blue,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}