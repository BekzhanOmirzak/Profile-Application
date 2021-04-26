package com.example.profileapplication3.ServerService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val builder=Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()



    fun<T> createInterFaceService(serviceType:Class<T>):T{
        return builder.create(serviceType)
    }

}