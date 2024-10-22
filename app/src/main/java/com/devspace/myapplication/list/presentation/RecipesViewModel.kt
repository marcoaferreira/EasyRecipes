package com.devspace.myapplication.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.list.data.listService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        listService.getRandom().enqueue(object : Callback<RecipesResponse> {
            override fun onResponse(
                call: Call<RecipesResponse>,
                response: Response<RecipesResponse>
            ) {
                if (response.isSuccessful) {
                    val recipes = response.body()?.recipes ?: emptyList()
                    _recipesList.value = recipes

                } else {
                    Log.d("RecipesViewModel", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<RecipesResponse>, t: Throwable) {
                Log.d("RecipesViewModel", "Network Error :: ${t.message}")
            }

        })
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