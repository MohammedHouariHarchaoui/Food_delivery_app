package com.example.tdm.retrofit

import com.example.tdm.data.*
import com.example.tdm.url
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*



interface Endpoint {
    //GETTING DATA
    @GET("restaurants")
    suspend fun getAllRestaurants(): Response<List<Restaurant>>

    @GET("dishes/{id}")
    suspend fun getDishesById(@Path("id") dishId: Int?): Response<List<Dish>>

    //POSTING DATA

    @Multipart
    @POST("users/auth/signup")
    suspend fun signUp(
        @Part("firstname") firstname: RequestBody,
        @Part("lastname") lastname: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("address") address: RequestBody,
        @Part("mdp") mdp: RequestBody,
        @Part("mail") mail: RequestBody,
        @Part profileImage: MultipartBody.Part
    ):Response<ResponseSignUp>

    @POST("orders/add")
    suspend fun submitCartItems(
        @Body order: Order
    ): Response<Any>

    @POST("reviews/review")
    suspend fun submitReview(
        @Body review:Review
    ):Response<Any>


    @POST("users/auth/login")
    suspend fun login(@Body signInBody: SignInBody):Response<Response2>

    @POST("reviews/userReview")
    suspend fun getReview(@Body getReviewBody: GetReviewBody):Response<GetR>

    companion object {
        @Volatile
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(Endpoint::class.java)
                }
            }
            return endpoint!!

        }


    }
}