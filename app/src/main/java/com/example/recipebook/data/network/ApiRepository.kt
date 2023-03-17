package com.example.recipebook.data.network

import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllRecipes() = apiService.getAllRecipes()
}