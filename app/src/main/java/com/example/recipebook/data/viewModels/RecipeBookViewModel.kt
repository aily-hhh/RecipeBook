package com.example.recipebook.data.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.data.models.Recipes
import com.example.recipebook.data.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeBookViewModel @Inject constructor(private val repository: ApiRepository): ViewModel() {

    private var _allRecipes: MutableLiveData<Recipes> = MutableLiveData()
    val allRecipes: LiveData<Recipes> get() = _allRecipes
    fun getAllRecipes() {
        viewModelScope.launch {
            repository.getAllRecipes().let {
                if (it.isSuccessful) {
                    _allRecipes.value = it.body()
                } else {
                    Log.d("checkData", "Failed to load recipes: ${it.errorBody()}")
                }
            }
        }
    }
}