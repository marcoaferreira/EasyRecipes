package com.devspace.myapplication.list.data


import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.list.data.local.RecipesLocalDataSource
import com.devspace.myapplication.list.data.remote.RecipeRemoteDataSource


class RecipeListRepository(
    private val local: RecipesLocalDataSource,
    private val remote: RecipeRemoteDataSource
) {

    suspend fun getRecipes(): Result<List<Recipe>?> {
        try {
            val result = remote.getRecipes()
            if(result.isSuccess){
                val recipesRemote = result.getOrNull() ?: emptyList()
                if(recipesRemote.isNotEmpty()){
                    local.updateLocalItems(recipesRemote)
                }
            } else {
                val localData = local.getLocalRecipes()
                if(localData.isEmpty()){
                    return result
                }
            }

            // source of truth
            return Result.success(local.getLocalRecipes())

        } catch (ex: Exception) {
            ex.printStackTrace()
            return Result.failure(ex)
        }
    }
}