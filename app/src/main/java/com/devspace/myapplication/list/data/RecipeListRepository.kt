package com.devspace.myapplication.list.data


import android.accounts.NetworkErrorException
import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.list.data.remote.listService

class RecipeListRepository(
   private val listService: listService
) {

    suspend fun getRecipes(): Result<RecipesResponse?>{
        return try {
            val response = listService.getRandom()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                    Result.failure(NetworkErrorException(response.message()))
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}