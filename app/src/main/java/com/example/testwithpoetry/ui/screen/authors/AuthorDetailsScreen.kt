package com.example.testwithpoetry.ui.screen.authors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testwithpoetry.PoetryRepository
import com.example.testwithpoetry.NetworkResource
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorDetailsScreen(
    navController: NavController,
    authorName: String,
    poetryRepository: PoetryRepository
) {
    val poemTitles = remember { mutableStateOf<List<String>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    val selectedPoem = remember { mutableStateOf<String?>(null) }
    val selectedPoemTitle = remember { mutableStateOf<String?>(null) }
    val showDialog = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(authorName) {
        val result = poetryRepository.getTitlesByAuthor(authorName)
        if (result is NetworkResource.Success) {
            poemTitles.value = result.data?.map { it.titles } ?: emptyList()
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("failed to load poems for $authorName")
            }
            // Handle error if needed
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = authorName) })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(poemTitles.value) { title ->
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                val result = poetryRepository.getPoem(authorName, title)
                                if (result is NetworkResource.Success) {
                                    val poems = result.data
                                    if (!poems.isNullOrEmpty()) {
                                        selectedPoemTitle.value = title
                                        selectedPoem.value = poems[0].lines?.joinToString("\n") ?: ""
                                        showDialog.value = true
                                    }  else {
                                        snackbarHostState.showSnackbar("Failed to load Poem: $title")
                                    }

                                }
                            }
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}