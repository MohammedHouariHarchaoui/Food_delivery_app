package com.example.tdm.room.data

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items_4")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    var cartItemId: Int = 0,
    val IdDish: Int,
    val quantity: Int,
    val dishNote: String,
    val dishName: String,
    val restaurantName: String,
    val image: String,
    val price: Double,
    val restaurantFee: Double?=0.0,
)

