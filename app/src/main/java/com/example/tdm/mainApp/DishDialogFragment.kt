package com.example.tdm.mainApp

import CartItemModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.tdm.data.CartItem
import com.example.tdm.databinding.DishDialogLayoutBinding

import com.example.tdm.models.DishModel
import com.example.tdm.models.RestaurantModel
import com.example.tdm.utilities.showPopupMessage
import kotlinx.coroutines.launch

class DishDialogFragment : DialogFragment() {
    lateinit var binding: DishDialogLayoutBinding
    lateinit var dishModel: DishModel
    lateinit var restaurantModel: RestaurantModel
    lateinit var cartItemModel: CartItemModel
    private var quantity = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DishDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dishModel = ViewModelProvider(requireActivity()).get(DishModel::class.java)
        restaurantModel = ViewModelProvider(requireActivity()).get(RestaurantModel::class.java)

        Glide.with(requireActivity()).load(dishModel.currentDish?.image).into(binding.dishImageView)
        binding.dialogDishNameTextView.text = dishModel.currentDish?.name
        binding.dishDialogPriceTextView.text = dishModel.currentDish?.price.toString() + " DZD"
        binding.descriptionTextView.text = dishModel.currentDish?.description
        binding.dishDialogTotalTextView.text = dishModel.currentDish?.price.toString() + " DZD"
        binding.minusBtn.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.dishQuantityTextView.text = quantity.toString()
                val totalPrice = (quantity * (dishModel.currentDish?.price?.toFloat() ?: 0f))
                binding.dishDialogTotalTextView.text = "$totalPrice DZD"
            }
        }

        binding.plusBtn.setOnClickListener {
            if (quantity < 100) {
                quantity++
                binding.dishQuantityTextView.text = quantity.toString()
                val totalPrice = (quantity * (dishModel.currentDish?.price?.toFloat() ?: 0f))
                binding.dishDialogTotalTextView.text = "$totalPrice DZD"

            }
        }

        binding.addToCartBtn.setOnClickListener {
            cartItemModel = ViewModelProvider(requireActivity()).get(CartItemModel::class.java)
            if (cartItemModel.restaurantId==-1 || cartItemModel.restaurantId==restaurantModel.currentRestaurant?.id){
                val cartItem = CartItem(
                    IdDish = dishModel.currentDish!!.id,
                    quantity = binding.dishQuantityTextView.text.toString().toInt(),
                    dishNote = binding.dishNoteTextTextMultiLine.text.toString(),
                    dishName = binding.dialogDishNameTextView.text.toString(),
                    restaurantName = restaurantModel.currentRestaurant!!.name,
                    image = dishModel.currentDish!!.image,
                    price = (binding.dishDialogPriceTextView.text.toString()).dropLast(4).toDouble(),
                    restuarantId = restaurantModel.currentRestaurant!!.id,
                    restaurantFee = restaurantModel.currentRestaurant?.deliveryFees
                )

                lifecycleScope.launch {
                    cartItemModel.addItem(cartItem)
                    //showPopupMessage(requireContext(),"Item Added successfully to the cart")
                    Toast.makeText(context, "Item Added successfully to the cart", Toast.LENGTH_SHORT).show()
                }
            }else{
                //showPopupMessage(requireContext(),"You can't add items from different restaurants to your cart")
                Toast.makeText(context, "You can't add items from different restaurants to your cart", Toast.LENGTH_SHORT).show()
            }

        }
    }
}