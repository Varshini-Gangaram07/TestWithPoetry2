package com.example.testwithpoetry.remoteResponses

import kotlinx.serialization.Serializable

@Serializable
data class AuthorsResponse(
    val authors: List<String>
)