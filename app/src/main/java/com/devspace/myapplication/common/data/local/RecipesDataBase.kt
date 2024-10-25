package com.devspace.myapplication.common.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database([RecipesEntity::class], version = 1)
abstract class RecipesDataBase: RoomDatabase() {

    abstract fun getRecipesDao(): RecipesDao
}