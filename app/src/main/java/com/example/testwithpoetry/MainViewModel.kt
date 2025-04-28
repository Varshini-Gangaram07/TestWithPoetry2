package com.example.testwithpoetry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: PoetryRepository
): ViewModel() {
    fun action() {
        viewModelScope.launch {
            val response = repo.getAuths()

            if (response is NetworkResource.Success) {
                response.data.authors.forEach {
                    println(it)
                } 
            }
        }
    }
}