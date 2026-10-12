
package com.pajjuaski3lab.earnmoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Blue = Color(0xFF1769FF)
private val DarkBlue = Color(0xFF1046B8)
private val PageBg = Color(0xFFF4F7FC)
private val Ink = Color(0xFF18243A)
private val Muted = Color(0xFF718096)
private val Green = Color(0xFF159B72)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Blue,
                    background = PageBg,
                    surface = Color.White
                )
            ) {
                EarnMoneyApp()
            }
        }
    }
}

private data class EarnTask(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val reward: Int
)

@Composable
fun EarnMoneyApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var balance by remember { mutableIntStateOf(120) }
    var claimed by remember { mutableStateOf(setOf<String>()) }
    var message by remember { mutableStateOf("") }

    val tasks = listOf(
        EarnTask("📅", "Daily Check-in", "ದಿನದ ಬೋನಸ್ ಪಡೆಯಿರಿ", 10),
        EarnTask("🎯", "Complete a Task", "Demo task ಪೂರ್ಣಗೊಳಿಸಿ", 20),
        EarnTask("👥", "Invite Friends", "ಸ್ನೇಹಿತರನ್ನು ಆಹ್ವಾನಿಸಿ", 25),
        EarnTask("⭐", "Daily Challenge", "ಹೊಸ ಸವಾಲನ್ನು ಪ್ರಯತ್ನಿಸಿ", 15)
    )

    Scaffold(
        containerColor = PageBg,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                val labels = listOf("Home", "Tasks", "Wallet", "Profile")
                val icons = listOf("🏠", "🎯", "💳", "👤")

                labels.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Text(icons[index], fontSize = 20.sp)
                        },
                        label = {
                            Text(label, fontSize = 11.sp)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Blue,
                            selectedTextColor = Blue,
                            indicatorColor = Color(0xFFE7EFFF),
                            unselectedTextColor = Muted
                        )
                    )
                }
            }
        },
        snackbarHost = {
            if (message.isNotBlank()) {
                Snackbar(
                    modifier = Modifier.padding(12.dp),
                    action = {
                        TextButton(onClick = { message = "" }) {
                            Text("OK", color = Color.White)
                        }
                    }
                ) {
                    Text(message)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    balance = balance,
                    tasks = tasks,
                    claimed = claimed,
                    onOpenTasks = { selectedTab = 1 },
                    onClaim = { task ->
                        if (claimed.contains(task.title)) {
                            message = "ಈ demo task ಈಗಾಗಲೇ claim ಆಗಿದೆ."
                        } else {
                            claimed = claimed + task.title
                            balance += task.reward
                            message = "Demo reward +₹${task.reward}"
                        }
                    }
                )

                1 -> TasksScreen(
                    tasks = tasks,
                    claimed = claimed,
                    onClaim = { task ->
                        if (claimed.contains(task.title)) {
                            message = "ಈ demo task ಈಗಾಗಲೇ claim ಆಗಿದೆ."
                        } else {
                            claimed = claimed + task.title
                            balance += task.reward
                            message = "Demo reward +₹${task.reward}"
                        }
                    }
                )

                2 -> WalletScreen(balance = balance)

                else -> ProfileScreen(
                    onAbout = {
                        message = "Earn Money — Demo Version"
                    }
                )
            }
        }
    }
}

@Composable
private fun PageHeader(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Text(
            title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Ink
        )
        Spacer(Modifier.height(4.dp))
        Text(subtitle, fontSize = 14.sp, color = Muted)
    }
}

@Composable
private fun HomeScreen(
    balance: Int,
    tasks: List<EarnTask>,
    claimed: Set<String>,
    onOpenTasks: () -> Unit,
    onClaim: (EarnTask) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            PageHeader(
                "Earn Money 💙",
                "ಸ್ವಾಗತ! ಇಂದೇ ನಿಮ್ಮ ಗುರಿ ಆರಂಭಿಸಿ."
            )
        }

        item {
            BalanceCard(balance)
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickCard(
                    emoji = "🎯",
                    title = "Tasks",
                    subtitle = "ಕೆಲಸ ಮಾಡಿ",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenTasks
                )
                QuickCard(
                    emoji = "🎁",
                    title = "Rewards",
                    subtitle = "ಬೋನಸ್ ನೋಡಿ",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenTasks
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Featured Tasks",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )
                TextButton(onClick = onOpenTasks) {
                    Text("ಎಲ್ಲವನ್ನೂ ನೋಡಿ")
                }
            }
        }

        items(tasks.take(3)) { task ->
            TaskCard(
                task = task,
                isClaimed = claimed.contains(task.title),
                onClaim = { onClaim(task) }
            )
        }

        item {
            Text(
                "ಗಮನಿಸಿ: ಇಲ್ಲಿ ತೋರಿಸಿರುವ rewards demo ಮಾತ್ರ.",
                modifier = Modifier.padding(20.dp),
                fontSize = 12.sp,
                color = Muted
            )
        }
    }
}

@Composable
private fun BalanceCard(balance: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Blue),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Blue, DarkBlue)
                    )
                )
                .padding(22.dp)
        ) {
            Column {
                Text(
                    "YOUR DEMO BALANCE",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "₹$balance",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(18.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("✨", fontSize = 20.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "ಚಿಕ್ಕ ಹೆಜ್ಜೆ, ದೊಡ್ಡ ಗುರಿ!",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickCard(
    emoji: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        Color(0xFFEAF0FF),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 22.sp)
            }

            Spacer(Modifier.height(12.dp))

            Text(
                title,
                fontWeight = FontWeight.Bold,
                color = Ink
            )
            Spacer(Modifier.height(4.dp))
            Text(
                subtitle,
                fontSize = 12.sp,
                color = Muted
            )
        }
    }
}

@Composable
private fun TasksScreen(
    tasks: List<EarnTask>,
    claimed: Set<String>,
    onClaim: (EarnTask) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(
            "Earn Tasks 🎯",
            "Task ಆಯ್ಕೆ ಮಾಡಿ, demo reward ಪಡೆಯಿರಿ."
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    isClaimed = claimed.contains(task.title),
                    onClaim = { onClaim(task) }
                )
            }
        }
    }
}

@Composable
private fun TaskCard(
    task: EarnTask,
    isClaimed: Boolean,
    onClaim: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 7.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        Color(0xFFEAF0FF),
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(task.emoji, fontSize = 25.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    task.title,
                    color = Ink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    task.subtitle,
                    color = Muted,
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    "+₹${task.reward}",
                    color = Green,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.width(6.dp))

            Button(
                onClick = onClaim,
                enabled = !isClaimed,
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (isClaimed) "Done" else "Claim",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun WalletScreen(balance: Int) {
    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(
            "My Wallet 💳",
            "ನಿಮ್ಮ demo balance ಇಲ್ಲಿ ನೋಡಿ."
        )

        BalanceCard(balance)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Wallet Information",
                    color = Ink,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(16.dp))

                InfoRow("Current demo balance", "₹$balance")
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PageBg
                )
                InfoRow("Payout status", "Not connected")
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = PageBg
                )
                InfoRow("Account type", "Demo")
            }
        }

        Text(
            "ನಿಜವಾದ ಬ್ಯಾಂಕ್ ಅಥವಾ UPI ಹಣ ವರ್ಗಾವಣೆ ಇನ್ನೂ ಸಂಪರ್ಕಗೊಂಡಿಲ್ಲ.",
            modifier = Modifier.padding(horizontal = 22.dp),
            fontSize = 13.sp,
            color = Muted
        )
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Muted, fontSize = 13.sp)
        Text(
            value,
            color = Ink,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun ProfileScreen(onAbout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(
            "My Profile 👤",
            "ನಿಮ್ಮ ಖಾತೆಯ ವಿವರಗಳು."
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .background(
                            Color(0xFFEAF0FF),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("👤", fontSize = 40.sp)
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    "Earn Money User",
                    fontSize = 20.sp,
                    color = Ink,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    "Demo Account",
                    color = Muted,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(20.dp))

                OutlinedButton(
                    onClick = onAbout,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("About Earn Money")
                }
            }
        }

        Text(
            "Privacy • Help • App Information",
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            color = Muted,
            fontSize = 13.sp
        )
    }
}
