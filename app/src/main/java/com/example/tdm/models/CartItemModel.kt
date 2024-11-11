import androidx.lifecycle.ViewModel
import com.example.tdm.data.CartItem
import com.example.tdm.room.dao.CartItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartItemModel: ViewModel() {
    private var cartItemDao: CartItemDao? = null
    var restaurantId :Int =-1
    var restaurantFee:Double?=0.0
    var data: MutableList<CartItem> = mutableListOf<CartItem>()

    suspend fun initialize(cartItemDao: CartItemDao?) {
        this.cartItemDao = cartItemDao
        loadData()
        if(data.size !=0){
            restaurantId = data[0].restuarantId
            restaurantFee=data[0].restaurantFee

        } else {
            restaurantId=-1
            restaurantFee=0.0
        }
    }


    private suspend fun loadData() {
        withContext(Dispatchers.IO) {
            val cartItemEntities = cartItemDao?.getCartItems()
            data.clear()
            if (cartItemEntities?.isNotEmpty() == true) {
                data.addAll(cartItemEntities.map { entity ->
                    CartItem(
                        IdDish= entity.IdDish,
                        quantity = entity.quantity,
                        dishNote= entity.dishNote,
                        dishName = entity.dishName,
                        restaurantName = entity.restaurantName,
                        image = entity.image,
                        price = entity.price,
                        restaurantFee = entity.restaurantFee

                    )
                })
            }
        }
    }


    suspend fun removeItem(position: Int) {
        if (position >= 0 && position < data.size) {
            val newData = data.toMutableList() // Create a copy of the list
            val id =data[position].cartItemId
            newData.removeAt(position) // Remove the item from the copy
            data.clear() // Clear the original list
            data.addAll(newData) // Add all items from the copy back to the original list

            // Perform the database operation to delete the item
            cartItemDao?.deleteCartItemById(id)
            if(data.size==0)restaurantId=-1
        }
    }

    suspend fun addItem(cartItem: CartItem) {
        withContext(Dispatchers.IO) {
            val insertedId = cartItemDao?.insertCartItem(
                com.example.tdm.room.data.CartItem(
                    IdDish= cartItem.IdDish,
                    dishName = cartItem.dishName,
                    dishNote= cartItem.dishNote,
                    restaurantName = cartItem.restaurantName,
                    image = cartItem.image,
                    price = cartItem.price,
                    quantity = cartItem.quantity ,
                    restaurantFee = cartItem.restaurantFee
                )
            ) ?: -1
            if (insertedId != -1L) {
                cartItem.cartItemId = insertedId // Update the cartItemId with the inserted ID
                data.add(cartItem) // Add the item to the data list
            }
        }
    }


    fun getTotal(): Double {
        var total=0.0
        for (item in data) {
            total += item.price * item.quantity
        }
        return total
    }

    suspend fun emptyCart() {
        withContext(Dispatchers.IO) {
            cartItemDao?.deleteAllCartItems()
            data.clear()
            restaurantId = -1
        }
    }
}
