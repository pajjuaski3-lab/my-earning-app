package com.gameworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Blue = Color(0xFF2979FF)
private val DarkBackground = Color(0xFF101522)
private val CardBackground = Color(0xFF1C2638)

data class Game(
    val name: String,
    val category: String,
    val description: String,
    val developer: String,
    val rating: String,
    val downloadUrl: String
)

private val games = listOf(
    Game(
        "Chess",
        "Puzzle",
        "Play the classic strategy board game.",
        "Game World",
        "4.5",
        "https://lichess.org"
    ),
    Game(
        "2048",
        "Puzzle",
        "Combine numbers to reach 2048.",
        "Game World",
        "4.4",
        "https://play2048.co"
    ),
    Game(
        "Speed Test",
        "Racing",
        "Explore racing games from official sources.",
        "Game World",
        "4.2",
        "https://play.google.com/store/games"
    ),
    Game(
        "Adventure Games",
        "Adventure",
        "Discover adventure games.",
        "Game World",
        "4.3",
        "https://play.google.com/store/games"
    ),
    Game(
        "Sports Games",
        "Sports",
        "Explore sports games.",
        "Game World",
        "4.2",
        "https://play.google.com/store/games"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Blue,
                    background = DarkBackground,
                    surface = CardBackground
                )
            ) {
                GameWorldApp()
            }
        }
    }
}

@Composable
fun GameWorldApp() {
    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedGame by remember { mutableStateOf<Game?>(null) }
    var favorites by remember { mutableStateOf(setOf<String>()) }

    val categories = listOf(
        "All", "Action", "Racing", "Puzzle",
        "Adventure", "Sports", "Shooting"
    )

    val filteredGames = games.filter { game ->
        (selectedCategory == "All" ||
            game.category == selectedCategory) &&
            game.name.contains(search, ignoreCase = true)
    }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🎮 Game World",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = CardBackground
            ) {
                NavigationBarItem(
                    selected = selectedGame == null,
                    onClick = { selectedGame = null },
                    icon = {
                        Icon(Icons.Default.Home, "Home")
                    },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        selectedCategory = "All"
                        search = ""
                    },
                    icon = {
                        Icon(Icons.Default.Search, "Search")
                    },
                    label = { Text("Explore") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        selectedGame = null
                        selectedCategory = "Favorites"
                    },
                    icon = {
                        Icon(Icons.Default.Favorite, "Favorites")
                    },
                    label = { Text("Favorites") }
                )
            }
        }
    ) { padding ->
        if (selectedGame != null) {
            GameDetailsScreen(
                game = selectedGame!!,
                isFavorite = selectedGame!!.name in favorites,
                onFavorite = {
                    favorites = if (selectedGame!!.name in favorites) {
                        favorites - selectedGame!!.name
                    } else {
                        favorites + selectedGame!!.name
                    }
                },
                modifier = Modifier.padding(padding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Discover Games",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = search,
                        onValueChange = { search = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Search games...")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        singleLine = true
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "Categories",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))
                }

                item {
                    androidx.compose.foundation.lazy.LazyRow(
                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            FilterChip(
                                selected = selectedCategory == category,
                                onClick = {
                                    selectedCategory = category
                                },
                                label = { Text(category) }
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Text(
                        "Featured Games",
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(10.dp))
                }

                items(filteredGames) { game ->
                    GameCard(
                        game = game,
                        isFavorite = game.name in favorites,
                        onClick = {
                            selectedGame = game
                        },
                        onFavorite = {
                            favorites = if (game.name in favorites) {
                                favorites - game.name
                            } else {
                                favorites + game.name
                            }
                        }
                    )

                    Spacer(Modifier.height(12.dp))
                }

                if (filteredGames.isEmpty()) {
                    item {
                        Text(
                            "No games found.",
                            color = Color.White
                        )
                    }
                }

                item {
                    Spacer(Modifier.height(20.dp))
                    Text(
                        "Game World • Discover your next game",
                        color = Color.LightGray,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GameCard(
    game: Game,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .background(
                        Blue,
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.SportsEsports,
                    contentDescription = "Game icon",
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    game.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Text(
                    game.category,
                    color = Color.LightGray
                )

                Text(
                    "⭐ ${game.rating}",
                    color = Color(0xFFFFD54F)
                )
            }

            IconButton(onClick = onFavorite) {
                Icon(
                    if (isFavorite) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle favorite",
                    tint = if (isFavorite) Color.Red else Color.White
                )
            }

            Button(onClick = onClick) {
                Text("View")
            }
        }
    }
}

@Composable
fun GameDetailsScreen(
    game: Game,
    isFavorite: Boolean,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            game.name,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        Text(
            "Category: ${game.category}",
            color = Color.LightGray
        )

        Text(
            "Developer: ${game.developer}",
            color = Color.LightGray
        )

        Text(
            "Rating: ⭐ ${game.rating}",
            color = Color(0xFFFFD54F)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            game.description,
            color = Color.White,
            fontSize = 17.sp
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onFavorite,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                if (isFavorite) Icons.Default.Favorite
                else Icons.Default.FavoriteBorder,
                contentDescription = null
            )

            Spacer(Modifier.width(8.dp))

            Text(
                if (isFavorite) "Remove Favorite"
                else "Add to Favorites"
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            "Official game website: ${game.downloadUrl}",
            color = Color.LightGray
        )

        Text(
            "Note: This starter screen displays the game link. " +
                "It does not download or install an APK.",
            color = Color.LightGray,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}