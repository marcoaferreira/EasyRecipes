package com.devspace.myapplication.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.detail.data.detailService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetailViewModel(
    private val detailService: detailService
): ViewModel() {

    private var _recipesDetail = MutableStateFlow<RecipeDto?>(null)
    val recipesDetail: StateFlow<RecipeDto?> = _recipesDetail

    init {

    }

    // * COULD NOT PASS RECIPE ID!!
   /* fun fetchDetail(id: Int){
        detailService.getRecipeInformation(id).enqueue(object : Callback<RecipeDto> {
            override fun onResponse(call: Call<RecipeDto>, response: Response<RecipeDto>) {
                if (response.isSuccessful) {
                    _recipesDetail.value = response.body()
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<RecipeDto>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }
        })
    }*/

    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService = RetrofitClient.retrofitInstance.create(detailService::class.java)
                return RecipeDetailViewModel(
                    detailService
                ) as T
            }
        }
    }
}