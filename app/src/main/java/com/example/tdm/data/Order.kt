package com.example.tdm.data

data class Order(
    val orderItems: List<OrderItem>,
    val address :String,
    val deliveryNote :String,
    val idUser:Int
)
