package com.example.ubereats_sim.model

data class MerchantSummary(
    val name: String,
    val rating: Double,
    val reviewCountText: String,
    val badge: String,
    val distanceText: String,
    val prepTimeText: String,
    val deliveryFeeText: String,
    val subtitle: String
)

data class MerchantMenuItem(
    val id: String,
    val name: String,
    val description: String = "",
    val price: Double,
    val priceText: String,
    val calorieText: String,
    val badgeText: String? = null,
    val options: List<ProductOption> = emptyList()
)

data class ProductOption(
    val name: String,
    val price: Double,
    val calorieText: String
)

data class MerchantProductSeedGroup(
    val merchantName: String? = null,
    val products: List<MerchantProductSeed>? = null
)

data class MerchantProductSeed(
    val name: String? = null,
    val price: Double? = null,
    val calorieText: String? = null,
    val badgeText: String? = null,
    val description: String? = null
)
