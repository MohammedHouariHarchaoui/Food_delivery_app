package com.example.tdm.data


data class Dish(
    val id:Int,
    val idRestaurant:Int,
    val price:Float,
    val name: String,
    val category: String,
    val description: String,
    val image: String,
    val createdAt: String, //useless
):java.io.Serializable
