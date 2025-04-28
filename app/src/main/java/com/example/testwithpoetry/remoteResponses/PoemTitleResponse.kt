package com.example.testwithpoetry.remoteResponses

import kotlinx.serialization.Serializable

@Serializable
data class PoemTitleResponse(
    val titles: String
)