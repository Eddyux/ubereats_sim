package com.example.ubereats_sim.presenter

import android.content.Context
import com.example.ubereats_sim.model.DataLoader
import com.example.ubereats_sim.model.PickupDealMarker
import com.example.ubereats_sim.model.PickupSpot
import com.example.ubereats_sim.model.Restaurant

class LocationPresenter(context: Context) {
    private val merchants: List<Restaurant> by lazy {
        (
            DataLoader.loadRestaurants(context) +
                DataLoader.loadGroceryMerchants(context) +
                DataLoader.loadConvenienceMerchants(context)
            )
            .distinctBy { it.name }
    }

    private val defaultMarkerPositions = listOf(
        0.12f to 0.18f,
        0.34f to 0.14f,
        0.58f to 0.20f,
        0.18f to 0.42f,
        0.46f to 0.40f,
        0.68f to 0.34f,
        0.26f to 0.66f,
        0.60f to 0.62f
    )

    private val markerPositionsByMerchant: Map<String, Pair<Float, Float>> by lazy {
        merchants.mapIndexed { index, merchant ->
            merchant.name to defaultMarkerPositions[index % defaultMarkerPositions.size]
        }.toMap()
    }

    fun getFilters(): List<String> {
        return listOf("Pickup", "Uber One", "Offers")
    }

    fun getMapMarkers(query: String = ""): List<PickupDealMarker> {
        return rankedMerchants(query)
            .take(6)
            .map { merchant ->
                val position = markerPositionsByMerchant[merchant.name] ?: (0.3f to 0.3f)
                PickupDealMarker(
                    storeName = merchant.name,
                    deal = merchant.discount?.takeIf { it.isNotBlank() } ?: "Pickup",
                    xPercent = position.first,
                    yPercent = position.second
                )
            }
    }

    fun getPickupSpots(query: String = ""): List<PickupSpot> {
        return rankedMerchants(query)
            .take(8)
            .map { merchant ->
                PickupSpot(
                    name = merchant.name,
                    eta = merchant.deliveryTime.ifBlank { "10-20 min" },
                    rating = merchant.rating
                )
            }
    }

    private fun rankedMerchants(query: String): List<Restaurant> {
        val normalizedQuery = normalizeQuery(query)
        if (normalizedQuery.isBlank()) {
            return merchants.take(8)
        }

        return merchants
            .map { merchant ->
                val score = matchScore(
                    query = normalizedQuery,
                    values = arrayOf(
                        merchant.name,
                        merchant.discount.orEmpty(),
                        merchant.tags.joinToString(" "),
                        merchant.section
                    )
                )
                merchant to score
            }
            .filter { it.second > 0 }
            .sortedWith(
                compareByDescending<Pair<Restaurant, Int>> { it.second }
                    .thenByDescending { it.first.rating }
                    .thenBy { it.first.name }
            )
            .map { it.first }
    }

    private fun matchScore(query: String, values: Array<String>): Int {
        val normalizedValues = values
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { it.lowercase() }

        var bestScore = 0
        normalizedValues.forEach { value ->
            when {
                value == query -> bestScore = maxOf(bestScore, 100)
                value.startsWith(query) -> bestScore = maxOf(bestScore, 80)
                value.contains(query) -> bestScore = maxOf(bestScore, 60)
                value.split(Regex("[^a-z0-9]+")).any { token -> token.startsWith(query) } ->
                    bestScore = maxOf(bestScore, 40)
            }
        }
        return bestScore
    }

    private fun normalizeQuery(query: String): String {
        return when (query.trim().lowercase()) {
            "mcd" -> "mcdonald"
            "bk" -> "burger king"
            "7 11" -> "7-eleven"
            else -> query.trim().lowercase()
        }
    }
}