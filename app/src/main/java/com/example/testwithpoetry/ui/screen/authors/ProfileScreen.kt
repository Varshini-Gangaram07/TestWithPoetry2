package com.example.testwithpoetry.ui.screen.authors

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(text = "Name: John Doe")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Email: johndoe@example.com")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Birthday: 01-01-1990")
        }
    }
}