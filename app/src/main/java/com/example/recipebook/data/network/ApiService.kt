package com.example.recipebook.data.network

import com.example.recipebook.data.models.Recipes
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("recipes.json")
    suspend fun getAllRecipes(): Response<Recipes>
}