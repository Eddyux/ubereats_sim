package com.example.ubereats_sim.model

data class MerchantCartItem(
    val merchantName: String,
    val product: MerchantMenuItem,
    val quantity: Int,
    val selectedOptions: Map<String, Int> = emptyMap()
)
