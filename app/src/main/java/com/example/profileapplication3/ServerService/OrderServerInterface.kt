package com.example.profileapplication3.ServerService

import com.example.profileapplication3.Models.Order
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderServerInterface {

     @POST("posts")
     fun sendToServer( order:Order):Call<Order>

}