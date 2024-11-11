package com.example.tdm.data

data class Restaurant(
    val id :Int,
    val name:String,
    val description:String,
    val logoPictureUrl:String,
    val backGroundImageUrl: String,
    val latitude:Double,
    val longitude:Double,
    val address :String,
    val cuisineType:String,
    val phoneNumber:String,
    val openingTime:String,
    val closingTime:String,
    val email:String,
    val instagram:String,
    val facebook:String,
    val averageRating:Float,
    val numberOfReviews:Int,
    val deliveryFees:Double,
    val createdAt:String //useless

//    val name:String,
//    val logo:Int,
//    val type:String,
//    val rating:Float,
//    val reviews:Int,
//    val phone:String,
//    val longitude:String,
//    val latitude:String,
//    val instagram:String,
//    val facebook:String,
//    val fee :Int =0,
//    val id:String
):java.io.Serializable
