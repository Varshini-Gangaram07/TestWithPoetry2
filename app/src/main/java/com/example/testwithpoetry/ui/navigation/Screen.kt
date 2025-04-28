package com.example.testwithpoetry.ui.navigation

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Authors : Screen("authors")
    object AuthorDetails : Screen("author_details/{authorName}") {
        fun createRoute(authorName: String): String {
            return "author_details/" + URLEncoder.encode(authorName, StandardCharsets.UTF_8.toString())
        }
        fun decodedName(encodedName: String): String {
            return URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString())
        }
    }
    object Home : Screen("home/{userName}") {
        fun createRoute(userName: String) = "home/$userName"
    }
    object Profile : Screen("profile")
}