package com.devspace.myapplication.list.data

import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.common.data.SearchRecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface listService {
    @GET("recipes/random?number=20")
    fun getRandom(): Call<RecipesResponse>

    @GET("/recipes/complexSearch?")
    fun searchRecipes(@Query("query") query: String): Call<SearchRecipesResponse>

}