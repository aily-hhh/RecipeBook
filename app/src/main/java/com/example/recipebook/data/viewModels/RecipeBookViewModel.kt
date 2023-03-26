package com.example.recipebook.data.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.data.models.Recipe
import com.example.recipebook.data.models.Recipes
import com.example.recipebook.data.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.Comparator
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

    private var _recomRecipes: MutableLiveData<Recipes> = MutableLiveData()
    val recomRecipes: LiveData<Recipes> get() = _recomRecipes
    fun recommendQuery(query: Int, currentName: String) {
        val filteredList = ArrayList<Recipe>()
        viewModelScope.launch {
            allRecipes.value?.recipes?.forEach { recipe ->
                if (recipe.difficulty == query && recipe.name != currentName) {
                    filteredList.add(recipe)
                }
            }
            _recomRecipes.postValue(Recipes(filteredList))
        }
    }

    fun performQuery(query: String) {
        val filteredList = ArrayList<Recipe>()
        viewModelScope.launch {
            delay(300)
            allRecipes.value?.recipes?.forEach { recipe ->
                if (recipe.name?.lowercase()?.contains(query.lowercase().trim()) == true
                    || recipe.description?.lowercase()?.contains(query.lowercase().trim()) == true
                    || recipe.instructions?.lowercase()?.contains(query.lowercase().trim()) == true
                ) {
                    filteredList.add(recipe)
                }
            }
            _allRecipes.postValue(Recipes(filteredList))
        }
    }

    fun sortDateAsc() {
        val sortedList = allRecipes.value?.recipes?.sortedBy { it.lastUpdated }
        _allRecipes.postValue(sortedList?.let { Recipes(it) })
    }

    fun sortDateDesc() {
        val sortedList = allRecipes.value?.recipes?.sortedByDescending { it.lastUpdated }
        _allRecipes.postValue(sortedList?.let { Recipes(it) })
    }

    fun sortNameAsc() {
        val sortedList = allRecipes.value?.recipes?.sortedBy { it.name }
        _allRecipes.postValue(sortedList?.let { Recipes(it) })
    }

    fun sortNameDesc() {
        val sortedList = allRecipes.value?.recipes?.sortedByDescending { it.name }
        _allRecipes.postValue(sortedList?.let { Recipes(it) })
    }

    fun getAllRecipesDateAsc() {
        viewModelScope.launch {
            repository.getAllRecipes().let {
                if (it.isSuccessful) {
                    val sortedList = it.body()?.recipes?.sortedBy { s -> s.lastUpdated }
                    _allRecipes.value = sortedList?.let { it1 -> Recipes(it1) }
                } else {
                    Log.d("checkData", "Failed to load recipes: ${it.errorBody()}")
                }
            }
        }
    }

    fun getAllRecipesDateDesc() {
        viewModelScope.launch {
            repository.getAllRecipes().let {
                if (it.isSuccessful) {
                    val sortedList = it.body()?.recipes?.sortedByDescending { s -> s.lastUpdated }
                    _allRecipes.value = sortedList?.let { it1 -> Recipes(it1) }
                } else {
                    Log.d("checkData", "Failed to load recipes: ${it.errorBody()}")
                }
            }
        }
    }

    fun getAllRecipesNameAsc() {
        viewModelScope.launch {
            repository.getAllRecipes().let {
                if (it.isSuccessful) {
                    val sortedList = it.body()?.recipes?.sortedBy { s -> s.name }
                    _allRecipes.value = sortedList?.let { it1 -> Recipes(it1) }
                } else {
                    Log.d("checkData", "Failed to load recipes: ${it.errorBody()}")
                }
            }
        }
    }

    fun getAllRecipesNameDesc() {
        viewModelScope.launch {
            repository.getAllRecipes().let {
                if (it.isSuccessful) {
                    val sortedList = it.body()?.recipes?.sortedByDescending { s -> s.name }
                    _allRecipes.value = sortedList?.let { it1 -> Recipes(it1) }
                } else {
                    Log.d("checkData", "Failed to load recipes: ${it.errorBody()}")
                }
            }
        }
    }
}