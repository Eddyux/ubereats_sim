package com.example.ubereats_sim.model

data class PickupDealMarker(
    val storeName: String,
    val deal: String,
    val xPercent: Float,
    val yPercent: Float
)

data class PickupSpot(
    val name: String,
    val eta: String,
    val rating: Double
)

data class SnackShortcut(
    val emoji: String,
    val label: String
)

data class SearchCategory(
    val label: String
)
