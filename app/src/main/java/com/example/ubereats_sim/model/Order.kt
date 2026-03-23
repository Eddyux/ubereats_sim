package com.example.ubereats_sim.model

data class Order(
    val id: String,
    val merchantName: String,
    val merchantLogo: String,
    val orderDate: String,
    val orderTime: String,
    val totalAmount: Double,
    val status: String,
    val items: List<OrderItem>,
    val estimatedArrival: String? = null,
    val latestArrival: String? = null,
    val deliveryStatus: String? = null,
    val driverName: String? = null,
    val driverRating: String? = null,
    val driverVehicle: String? = null,
    val merchantAddress: String? = null,
    val deliveryAddress: String? = null,
    val promoMessage: String? = null
)

data class OrderItem(
    val name: String,
    val quantity: Int,
    val price: Double
)
