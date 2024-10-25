package com.devspace.myapplication

import com.devspace.myapplication.common.data.SearchRecipesResponse
import com.devspace.myapplication.common.data.remote.model.RecipeDto
import com.devspace.myapplication.common.data.remote.model.RecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/random?number=20")
    fun getRandom(): Call<RecipesResponse>

    @GET("/recipes/complexSearch?")
    fun searchRecipes(@Query("query") query: String): Call<SearchRecipesResponse>

    @GET("recipes/{id}/information?includeNutrition=false")
    fun getRecipeInformation(@Path("id") id: String): Call<RecipeDto>
}