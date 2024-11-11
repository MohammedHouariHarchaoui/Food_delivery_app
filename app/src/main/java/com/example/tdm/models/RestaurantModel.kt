package com.example.tdm.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tdm.data.Restaurant
import com.example.tdm.retrofit.Endpoint
import kotlinx.coroutines.*

class RestaurantModel:ViewModel() {

    val restaurants = MutableLiveData<List<Restaurant>>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    var currentRestaurant: Restaurant? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch   {
            loading.value = false
            errorMessage.value = "An error has occurred"
        }
    }

    fun loadRestaurants() {
        if(restaurants.value==null) {
            loading.value = true
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = Endpoint.createEndpoint().getAllRestaurants()
                withContext(Dispatchers.Main) {
                    loading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        restaurants.value = response.body()

                    } else {
                        errorMessage.value = "An error has occurred"
                    }
                }
            }
        }

    }

    fun setCurrentRestaurant(position: Int) {
        val restaurantList = restaurants.value
        if (restaurantList != null && position >= 0 && position < restaurantList.size) {
            currentRestaurant = restaurantList[position]
        } else {
            currentRestaurant = null
        }
    }
}