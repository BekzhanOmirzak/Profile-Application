package com.example.profileapplication3.Models

data class Product (
         var productId:Int=0,
         val name:String="No Name",
         val description:String="No description",
         val imageURL:String="No ImageURl",
         val category: String="No Category",
         var price:Double=0.00,
         var availableAmount:Int=10,
         var rate:Int=0,
         var userPoint:Int=0,
         val popularityPoint:Int=0,
         var product_firebase_id:String="No Id"
        )