package com.example.tdm.mainApp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdm.models.RestaurantModel
import com.example.tdm.adapters.RestaurantAdapter
import com.example.tdm.databinding.FragmentRestaurantBinding
import com.example.tdm.loginApp.LoginActivity
import com.example.tdm.loginApp.SessionManager


class RestaurantFragment : Fragment() {
    lateinit var binding: FragmentRestaurantBinding
    lateinit var restaurantModel : RestaurantModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restaurantModel =ViewModelProvider(requireActivity()).get(RestaurantModel::class.java)
        binding.restaurantRecyclerView.layoutManager=LinearLayoutManager(requireActivity())
        sessionManager = SessionManager(requireContext())
        val adapter = RestaurantAdapter(requireActivity(),restaurantModel)
        binding.restaurantRecyclerView.adapter=adapter

        restaurantModel.loadRestaurants()

        restaurantModel.loading.observe(requireActivity()) { loading ->
            if (loading) {
                binding.restaurantsProgressBar.visibility = View.VISIBLE
            } else {
                binding.restaurantsProgressBar.visibility = View.GONE
            }
        }

        restaurantModel.errorMessage.observe(requireActivity()) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        restaurantModel.restaurants.observe(requireActivity()) { restaurants ->
            adapter.setRestaurants(restaurants)
        }


        binding.logOutBtn.setOnClickListener(){
            logout()
        }
    }

    private fun logout() {
        // Clear the session and navigate to the LoginActivity
        sessionManager.isLoggedIn = false
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }


}