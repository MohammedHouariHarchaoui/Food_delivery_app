package com.example.tdm.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tdm.*
import com.example.tdm.models.RestaurantModel
import com.example.tdm.data.Restaurant
import com.example.tdm.databinding.RestaurantLayoutBinding
import com.example.tdm.viewHolders.RestaurantViewHolder

class RestaurantAdapter (val context: Context, val viewModel : RestaurantModel):
    RecyclerView.Adapter<RestaurantViewHolder>() {

    var data = mutableListOf<Restaurant>()

    fun setRestaurants(restaurants: List<Restaurant>) {
        this.data = restaurants.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(RestaurantLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int, ) {
        holder.binding.apply {
            nameTextView.text=data[position].name
            typeTextView.text=data[position].cuisineType
            ratingBar.rating=data[position].averageRating
            Glide.with(context).load(data[position].logoPictureUrl).into(logoImageView)
            reviewsText.text=data[position].numberOfReviews.toString()
        }

        holder.binding.facebookIcon.setOnClickListener(){
            openLink(this.context,data[position].facebook)

        }
        holder.binding.instagramIcon.setOnClickListener(){
            openLink(this.context,data[position].instagram)

        }

        holder.binding.locationIcon.setOnClickListener(){
            openLocation(this.context,data[position].latitude.toString(),data[position].longitude.toString())

        }
        holder.binding.phoneIcon.setOnClickListener(){
            openNumber(this.context,data[position].phoneNumber)

        }

        holder.itemView.setOnClickListener(){
                view: View ->view.findNavController().navigate(R.id.action_restaurantFragment_to_restaurantMenuFragment)
                viewModel.setCurrentRestaurant(position)

        }
    }
}
