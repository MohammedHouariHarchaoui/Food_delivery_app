package com.example.tdm.data


data class CartItem(
    var cartItemId: Long=0,
    val IdDish: Int,
    val quantity: Int,
    val dishNote: String,
    val dishName: String,
    val restaurantName: String,
    val restaurantFee: Double?=0.0,
    val image: String,
    val price: Double,
    val restuarantId :Int=0
):java.io.Serializable
