package com.example.profileapplication3.Models

data  class Review(
    var review_id:Int=0,
    val product_fire_id:String="No product_firebase_id",
    val name:String="No name",
    val date:String="No date",
    val review:String="No review"
)