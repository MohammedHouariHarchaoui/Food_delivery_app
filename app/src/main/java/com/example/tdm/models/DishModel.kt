package com.example.tdm.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tdm.R
import com.example.tdm.data.Dish
import com.example.tdm.data.Restaurant
import com.example.tdm.retrofit.Endpoint
import kotlinx.coroutines.*

class DishModel:ViewModel() {

    val dishes = MutableLiveData<List<Dish>>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    var currentDish: Dish? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch   {
            loading.value = false
            errorMessage.value = "An error has occurred"
        }
    }

    fun loadDishes(id:Int?) {
        loading.value = true
        val currentDishes = dishes.value
        if(currentDishes==null || currentDishes.isEmpty() ||currentDishes[0].idRestaurant!=id) {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = Endpoint.createEndpoint().getDishesById(id)
                withContext(Dispatchers.Main) {
                    loading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        dishes.value = response.body()

                    } else {
                        dishes.value = emptyList()
                        errorMessage.value = "An error has occurred"
                    }
                }
            }
        }
        loading.value = false
    }

    fun setCurrentRestaurant(position: Int) {
        val dishList = dishes.value
        currentDish = if (dishList != null && position >= 0 && position < dishList.size) {
            dishList[position]
        } else {
            null
        }
    }
}