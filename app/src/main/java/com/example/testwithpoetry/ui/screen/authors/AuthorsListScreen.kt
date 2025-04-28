package com.example.testwithpoetry.ui.screen.authors

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorsListScreen(navController: NavController, poetryRepository: PoetryRepository) {
    var authors by remember { mutableStateOf<List<String>>(emptyList()) }
    var favoriteAuthors by remember { mutableStateOf(setOf<String>()) }
    var snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        when ( val result = poetryRepository.getAuths()) {
            is NetworkResource.Success -> {
                authors = result.data?.authors ?: emptyList()
            }
            else -> {
                //Handle Error Optional
            }

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Welcome") }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(authors) { author ->
                AuthorItem(
                    author = author,
                    isFavorite = favoriteAuthors.contains(author),
                    onStarClicked = {
                        if (favoriteAuthors.contains(author)) {
                            favoriteAuthors -= author
                        } else {
                            favoriteAuthors += author
                            scope.launch {
                                snackbarHostState.showSnackbar("Author added to favorites")
                            }
                        }
                    },
                    onAuthorClicked = {
                        navController.navigate(Screen.AuthorDetails.createRoute(author))
                    }
                )
            }
        }
    }
}

@Composable
fun AuthorItem(
    author: String,
    isFavorite: Boolean,
    onStarClicked: () -> Unit,
    onAuthorClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAuthorClicked() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = author,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { onStarClicked() }) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Favorite",
                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}