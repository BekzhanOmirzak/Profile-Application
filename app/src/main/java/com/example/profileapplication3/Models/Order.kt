package com.example.profileapplication3.Models

data class Order(
    var order_id:Int=0,
    val address:String="No Address",
    val email:String="No Email",
    val phoneNumber:String="No Phone Number",
    val zipCode:String="No zipCode",
    val name:String="No Name",
    val purchased_items:String="No purchased Items",
    var order_fire_id:String="No id",
    var price: Double=0.00,
    var payment_method:String="No payment",
    var success:Boolean=false
)