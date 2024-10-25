package com.devspace.myapplication

import android.app.Application
import androidx.room.Room
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.data.local.RecipesDataBase
import com.devspace.myapplication.list.data.RecipeListRepository
import com.devspace.myapplication.list.data.local.RecipesLocalDataSource
import com.devspace.myapplication.list.data.remote.RecipeRemoteDataSource
import com.devspace.myapplication.list.data.remote.listService

class RecipesApplication: Application() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipesDataBase::class.java, "database-recipes"
        ).build()
    }

    private val listService by lazy{
        RetrofitClient.retrofitInstance.create(listService::class.java)
    }

    private val localDataSource: RecipesLocalDataSource by lazy {
        RecipesLocalDataSource(db.getRecipesDao())
    }

    private val remoteDataSource: RecipeRemoteDataSource by lazy {
        RecipeRemoteDataSource(listService)
    }

    val repository: RecipeListRepository by lazy {
        RecipeListRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }
}