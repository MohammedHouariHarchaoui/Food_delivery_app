package com.example.tdm.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tdm.room.data.CartItem

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cart_items_4")
    fun getCartItems(): List<CartItem>

    @Query("DELETE  FROM cart_items_4")
    fun deleteAllCartItems()

    @Insert
    fun insertCartItem(cartItem: CartItem): Long

    @Delete
    fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_items_4 WHERE cartItemId = :itemId")
    fun deleteCartItemById(itemId: Long)
}