package com.example.testwithpoetry

import com.example.testwithpoetry.remoteResponses.AuthorsResponse
import com.example.testwithpoetry.remoteResponses.PoemResponse
import com.example.testwithpoetry.remoteResponses.PoemTitleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PoetryRepository @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getAuths(): NetworkResource<AuthorsResponse> {
        return withContext(Dispatchers.IO) {
            val response = client.get("https://poetrydb.org/author")

            if (response.status.value == 200) {
                NetworkResource.Success(response.body())
            } else {
                NetworkResource.Fail(response.status.description)
            }
        }
    }

    suspend fun getTitlesByAuthor(authorName: String): NetworkResource<List<PoemTitleResponse>> {
        return withContext(Dispatchers.IO) {
            val response = client.get("https://poetrydb.org/author/$authorName/title")

            if (response.status.value == 200) {
                NetworkResource.Success(response.body())
            } else {
                NetworkResource.Fail(response.status.description)
            }
        }
    }

    suspend fun getPoem(authorName: String, title: String): NetworkResource<List<PoemResponse>> {
        return withContext(Dispatchers.IO) {
            val response = client.get("https://poetrydb.org/author,title/$authorName;$title")

            if (response.status.value == 200) {
                val poems = response.body<List<PoemResponse>>()
                if (poems.isNotEmpty()) {
                    delay(3000L) // This is the only code line that you can't change
                    NetworkResource.Success(poems[0])
                } else {
                    NetworkResource.Fail("No Poems Found.")
                }

                NetworkResource.Success(response.body())
            } else {
                NetworkResource.Fail(response.status.description)
            }
        }
    }
}