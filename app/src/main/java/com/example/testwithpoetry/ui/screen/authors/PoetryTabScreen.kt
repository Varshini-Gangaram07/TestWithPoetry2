package com.example.testwithpoetry.ui.screen.authors
import com.example.testwithpoetry.ui.screen.authors.AuthorsListScreen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testwithpoetry.NetworkResource
import com.example.testwithpoetry.PoetryRepository
import com.example.testwithpoetry.ui.navigation.Screen
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color

@Composable
fun PoetryTabScreen(
    navController: NavController,
    poetryRepository: PoetryRepository
) {
    val authors = remember { mutableStateOf<List<String>>(emptyList()) }
    val favoriteAuthors = remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        val result = poetryRepository.getAuths()
        if (result is NetworkResource.Success) {
            authors.value = result.data?.authors ?: emptyList()
        }
    }

    LazyColumn {
        items(authors.value) { author ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screen.AuthorDetails.createRoute(author))
                    }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = author)
                IconButton(onClick = {
                    if (favoriteAuthors.value.contains(author)) {
                        favoriteAuthors.value = favoriteAuthors.value - author
                    } else {
                        favoriteAuthors.value = favoriteAuthors.value + author
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (favoriteAuthors.value.contains(author)) Color.Yellow else Color.Gray
                    )
                }
            }
        }
    }
}