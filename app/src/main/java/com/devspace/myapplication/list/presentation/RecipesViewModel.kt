package com.devspace.myapplication.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.RecipesApplication
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.data.remote.model.RecipeDto
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
                val recipes = result.getOrNull()?: emptyList()
                _recipesList.value = recipes.map {
                    RecipeDto(
                        id = it.id,
                        title = it.title,
                        image = it.image,
                        summary = it.summary
                    )
                }

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
                val application = checkNotNull(extras[APPLICATION_KEY])
                return RecipesViewModel(
                    repository = (application as RecipesApplication).repository
                ) as T
            }
        }
    }
}