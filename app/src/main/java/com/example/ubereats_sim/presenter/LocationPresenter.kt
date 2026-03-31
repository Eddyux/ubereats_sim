package com.example.ubereats_sim.presenter

import com.example.ubereats_sim.model.PickupDealMarker
import com.example.ubereats_sim.model.PickupSpot

class LocationPresenter {
    private val markers = listOf(
        PickupDealMarker("McDonald's", "$5 off", 0.34f, 0.18f),
        PickupDealMarker("Burger King", "20% off", 0.10f, 0.22f),
        PickupDealMarker("Popeyes", "Buy 1, get 1", 0.56f, 0.30f),
        PickupDealMarker("Target", "10% off", 0.25f, 0.58f),
        PickupDealMarker("7-Eleven", "Deals", 0.62f, 0.64f)
    )

    private val pickupSpots = listOf(
        PickupSpot("McDonald's", "9-16 min", 4.6),
        PickupSpot("Burger King", "10-20 min", 4.3),
        PickupSpot("Popeyes", "15-25 min", 4.2),
        PickupSpot("7-Eleven", "8-15 min", 4.0)
    )

    fun getFilters(): List<String> {
        return listOf("Pickup", "Uber One", "Offers")
    }

    fun getMapMarkers(query: String = ""): List<PickupDealMarker> {
        val normalizedQuery = normalizeQuery(query)
        if (normalizedQuery.isBlank()) return markers
        return markers.filter { marker ->
            marker.storeName.lowercase().contains(normalizedQuery) ||
                marker.deal.lowercase().contains(normalizedQuery)
        }
    }

    fun getPickupSpots(query: String = ""): List<PickupSpot> {
        val normalizedQuery = normalizeQuery(query)
        if (normalizedQuery.isBlank()) return pickupSpots
        return pickupSpots.filter { spot ->
            spot.name.lowercase().contains(normalizedQuery)
        }
    }

    private fun normalizeQuery(query: String): String {
        return when (query.trim().lowercase()) {
            "麦当劳" -> "mcdonald"
            else -> query.trim().lowercase()
        }
    }
}
