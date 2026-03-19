package com.example.ubereats_sim.model

data class Restaurant(
    val id: String,
    val name: String,
    val imageUrl: String,
    val deliveryFee: String,
    val deliveryTime: String,
    val rating: Double,
    val reviewCount: Int,
    val discount: String? = null,
    val tags: List<String> = emptyList(),
    val section: String = ""
)

data class NearbyStore(
    val id: String,
    val name: String,
    val icon: String
)
