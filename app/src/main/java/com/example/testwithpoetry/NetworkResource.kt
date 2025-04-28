package com.example.testwithpoetry

sealed class NetworkResource<T> {
    data class Success<T>(val data: T) : NetworkResource<T>()
    data class Fail<T>(val error: String) : NetworkResource<T>()
}