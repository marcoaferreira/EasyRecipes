package com.devspace.myapplication.list.data.remote

import com.devspace.myapplication.common.data.remote.model.RecipesResponse
import com.devspace.myapplication.common.data.SearchRecipesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface listService {
    @GET("recipes/random?number=20")
    suspend fun getRandom(): Response<RecipesResponse>

    @GET("/recipes/complexSearch?")
    fun searchRecipes(@Query("query") query: String): Call<SearchRecipesResponse>

}