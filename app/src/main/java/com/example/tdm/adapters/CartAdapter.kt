package com.example.tdm.adapters


import CartDialogFragment
import CartItemModel
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tdm.data.CartItem
import com.example.tdm.databinding.CartItemLayoutBinding


import com.example.tdm.viewHolders.CartItemViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartAdapter(
    val context: Context,
    val data: MutableList<CartItem>,
    val viewModel: CartItemModel,
    val dialog: CartDialogFragment,
) : RecyclerView.Adapter<CartItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = data.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = data[position]
        holder.binding.apply {
            cartDishNameTextView.text = item.dishName
            cartDishQuantityTextView.text = item.quantity.toString()
            cartDishPriceTextView.text = "${item.price} DZD"
            cartItemTotalTextView.text = "${item.quantity * item.price} DZD"
            Glide.with(context).load(item.image).into(cartDishImageView)
        }

        holder.binding.cartItemRemoveimageView.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) {
                        viewModel.removeItem(adapterPosition)
                    }
                    notifyItemRemoved(adapterPosition)
                    dialog.updateUi()
                }
            }
        }
    }
}


