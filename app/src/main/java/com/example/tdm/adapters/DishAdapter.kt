package com.example.tdm.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tdm.data.Dish
import com.example.tdm.data.Restaurant
import com.example.tdm.databinding.DishLayoutBinding
import com.example.tdm.mainApp.RestaurantMenuFragment
import com.example.tdm.models.DishModel
import com.example.tdm.viewHolders.DishViewHolder

class DishAdapter (val context: Context, val viewModel : DishModel,val fragment : RestaurantMenuFragment):
    RecyclerView.Adapter<DishViewHolder>() {

    var data = mutableListOf<Dish>()

    fun setDishes(dishes: List<Dish>) {
        this.data = dishes.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        return DishViewHolder(DishLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun getItemCount() = data.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DishViewHolder, position: Int, ) {
        holder.binding.apply {
            dishNameTextView.text=data[position].name
            dishPriceTextView.text=data[position].price.toString()+" DZD"
            Glide.with(context).load(data[position].image).into(dishImageView)
        }


        holder.itemView.setOnClickListener(){
            viewModel.setCurrentRestaurant(position)
            fragment.showDish()
        }
    }
}
