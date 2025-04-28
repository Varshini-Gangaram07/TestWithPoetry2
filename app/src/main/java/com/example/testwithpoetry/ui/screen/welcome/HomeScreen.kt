package com.example.testwithpoetry.ui.screen.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testwithpoetry.PoetryRepository
import com.example.testwithpoetry.ui.navigation.Screen
import com.example.testwithpoetry.ui.screen.authors.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, userName: String, poetryRepository: PoetryRepository) {
    var selectedTab by remember { mutableStateOf(-1) } // 0 -> Poetry, 1 -> Account

    Scaffold(
        topBar = {
            when (selectedTab) {
                0 -> TopAppBar(
                    title = { Text(text = "Poetry") }
                )
                1 -> TopAppBar(
                    title = { Text(text = "Profile") }
                )
                else -> TopAppBar(
                    title = { Text(text = "Welcome, $userName") }
                )

            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    label = { Text("Poetry") },
                    icon = { }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    label = { Text("Account") },
                    icon = { }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> PoetryTabScreen(navController = navController, poetryRepository = poetryRepository)
                1 -> ProfileScreen()
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Welcome! Please select a tab.")
                    }
                }
            }
        }
    }
}