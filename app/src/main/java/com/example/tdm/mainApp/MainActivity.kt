package com.example.tdm.mainApp

import CartDialogFragment
import CartItemModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.tdm.databinding.ActivityMainBinding
import com.example.tdm.room.AppDatabase
import com.example.tdm.room.AppDatabase.Companion.buildDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var cartItemModel: CartItemModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val appDatabase = buildDatabase(this)
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            cartItemModel = CartItemModel()
            cartItemModel.initialize(appDatabase?.cartItemDao())
        }

        binding.cartImageView.setOnClickListener(){
            showCart()
        }


    }
    private fun showCart (){
        val dialogFragment= CartDialogFragment()
        dialogFragment.show(supportFragmentManager, "")
    }
}