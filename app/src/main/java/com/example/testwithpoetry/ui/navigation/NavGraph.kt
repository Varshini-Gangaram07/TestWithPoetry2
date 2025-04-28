package com.example.testwithpoetry.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testwithpoetry.PoetryRepository
import com.example.testwithpoetry.ui.screen.authors.AuthorDetailsScreen
import com.example.testwithpoetry.ui.screen.authors.AuthorsListScreen
import com.example.testwithpoetry.ui.screen.authors.ProfileScreen
import com.example.testwithpoetry.ui.screen.welcome.WelcomeScreen
import com.example.testwithpoetry.ui.screen.welcome.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    poetryRepository: PoetryRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Authors.route) {
            AuthorsListScreen(navController = navController, poetryRepository = poetryRepository)
        }
        composable(
            route = Screen.AuthorDetails.route,
            arguments = listOf(navArgument("authorName") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedAuthorName = backStackEntry.arguments?.getString("authorName") ?: ""
            val authorName = Screen.AuthorDetails.decodedName(encodedAuthorName)
            AuthorDetailsScreen(
                navController = navController,
                authorName = authorName,
                poetryRepository = poetryRepository
            )
        }
        composable(route = Screen.Home.route,
            arguments = listOf(navArgument("userName") {type = NavType.StringType})) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            HomeScreen(
                navController = navController,
                userName = userName,
                poetryRepository = poetryRepository
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }
    }
}