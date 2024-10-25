package com.devspace.myapplication

import android.app.Application
import androidx.room.Room
import com.devspace.myapplication.common.data.local.RecipesDataBase

class RecipesApplication: Application() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipesDataBase::class.java, "database-recipes"
        ).build()
    }
}