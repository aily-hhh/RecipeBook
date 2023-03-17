package com.example.recipebook.data.models

data class Recipe(
    val description: String,
    val difficulty: Int,
    val images: List<String>,
    val instructions: String,
    val lastUpdated: Int,
    val name: String,
    val uuid: String
)