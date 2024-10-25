package com.devspace.myapplication.list.data.local

import com.devspace.myapplication.common.data.local.RecipesDao
import com.devspace.myapplication.common.data.local.RecipesEntity
import com.devspace.myapplication.common.data.model.Recipe

class RecipesLocalDataSource(
    private val dao: RecipesDao
) {

    suspend fun getLocalRecipes(): List<Recipe> {
        val recipesEntity = dao.getRecipes()
        return recipesEntity.map {
            Recipe(
                id = it.id,
                title = it.title,
                image = it.image,
                summary = it.summary
            )
        }
    }

    suspend fun updateLocalItems(recipes: List<Recipe>){
        val entities = recipes.map{
            RecipesEntity(
                id = it.id,
                title = it.title,
                image = it.image,
                summary = it.summary
            )
        }
        dao.insertAll(entities)
    }
}