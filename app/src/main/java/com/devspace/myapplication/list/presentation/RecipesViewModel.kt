package com.devspace.myapplication.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.list.data.RecipeListRepository
import com.devspace.myapplication.list.data.remote.listService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class RecipesViewModel(
    private val repository: RecipeListRepository
) : ViewModel() {
    private val _recipesList = MutableStateFlow<List<RecipeDto>>(emptyList())
    val recipesList: StateFlow<List<RecipeDto>> = _recipesList

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getRecipes()
            if (result.isSuccess) {
                val recipes = result.getOrNull()?.recipes?: emptyList()
                _recipesList.value = recipes

            } /*else {
                val ex = result.exceptionOrNull()
                if(ex is UnknownHostException){

                }
            }*/

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(listService::class.java)
                return RecipesViewModel(
                    repository = RecipeListRepository(listService)
                ) as T
            }
        }
    }
}