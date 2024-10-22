package com.devspace.myapplication.common.model

data class RecipesResponse(
    val recipes: List<RecipeDto>
)


data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String
)