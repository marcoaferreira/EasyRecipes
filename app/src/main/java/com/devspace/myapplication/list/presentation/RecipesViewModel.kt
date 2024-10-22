package com.devspace.myapplication.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.list.data.listService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesViewModel(
   private val listService: listService
) : ViewModel() {
    private val _recipesList = MutableStateFlow<List<RecipeDto>>(emptyList())
    val recipesList: StateFlow<List<RecipeDto>> = _recipesList

    init {
        fetchRecipes()
    }

    private fun fetchRecipes(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getRandom()
            if (response.isSuccessful) {
                val recipes = response.body()?.recipes ?: emptyList()
                _recipesList.value = recipes

            } else {
                Log.d("RecipesViewModel", "Request Error :: ${response.errorBody()}")
            }
        }

    }

    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(listService::class.java)
                return RecipesViewModel(
                    listService
                ) as T
            }
        }
    }
}