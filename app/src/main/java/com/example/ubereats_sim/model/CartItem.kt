package com.example.ubereats_sim.model

data class CartItem(
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImage: String,
    val itemCount: Int,
    val totalPrice: Double,
    val deliveryAddress: String
)
