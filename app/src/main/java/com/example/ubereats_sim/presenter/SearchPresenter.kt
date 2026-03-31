package com.example.ubereats_sim.presenter

import android.content.Context
import com.example.ubereats_sim.model.DataLoader
import com.example.ubereats_sim.model.Restaurant
import com.example.ubereats_sim.model.SearchCategory
import com.example.ubereats_sim.model.SearchDishResult
import com.example.ubereats_sim.model.SearchMerchantResult
import com.example.ubereats_sim.model.SearchResults
import com.example.ubereats_sim.model.SnackShortcut

class SearchPresenter(context: Context) {
    private val merchants: List<Restaurant> by lazy {
        (
            DataLoader.loadRestaurants(context) +
                DataLoader.loadGroceryMerchants(context) +
                DataLoader.loadConvenienceMerchants(context)
            )
            .distinctBy { it.name }
    }

    private val dishes: List<SearchDishResult> by lazy {
        DataLoader.loadMerchantProductSeeds(context)
            .flatMap { (merchantName, products) ->
                products.mapNotNull { seed ->
                    val name = seed.name?.trim().orEmpty()
                    val price = seed.price ?: return@mapNotNull null
                    if (name.isBlank()) return@mapNotNull null
                    SearchDishResult(
                        merchantName = merchantName,
                        dishName = name,
                        priceText = "US$${String.format("%.2f", price)}"
                    )
                }
            }
    }

    fun getRecentSearches(): List<String> {
        return listOf("Pizza", "Sushi", "Kosher", "Burger King")
    }

    fun getSnackShortcuts(): List<SnackShortcut> {
        return listOf(
            SnackShortcut("🍟", "Chips"),
            SnackShortcut("🍿", "Popcorn"),
            SnackShortcut("🍫", "Chocolate"),
            SnackShortcut("🥤", "Soda")
        )
    }

    fun getTopCategories(): List<SearchCategory> {
        return listOf(
            SearchCategory("Kosher"),
            SearchCategory("Pizza"),
            SearchCategory("Sushi"),
            SearchCategory("Burgers"),
            SearchCategory("Sandwiches"),
            SearchCategory("Coffee")
        )
    }

    fun search(query: String): SearchResults {
        val normalizedQuery = normalizeQuery(query)
        if (normalizedQuery.isBlank()) return SearchResults()

        val merchantResults = merchants
            .map { merchant ->
                val score = matchScore(
                    normalizedQuery,
                    merchant.name,
                    merchant.discount.orEmpty(),
                    merchant.tags.joinToString(" ")
                )
                merchant to score
            }
            .filter { it.second > 0 }
            .sortedByDescending { it.second }
            .take(8)
            .map { (merchant, _) ->
                SearchMerchantResult(
                    name = merchant.name,
                    subtitle = merchant.tags.joinToString(" / ").ifBlank { "Restaurant" },
                    metaLine = "${merchant.deliveryTime} / ${merchant.deliveryFee} / ${merchant.rating} stars"
                )
            }

        val dishResults = dishes
            .map { dish ->
                val score = matchScore(normalizedQuery, dish.dishName, dish.merchantName)
                dish to score
            }
            .filter { it.second > 0 }
            .sortedByDescending { it.second }
            .take(10)
            .map { it.first }

        val categoryResults = (
            getTopCategories().map { it.label } +
                merchants.flatMap { it.tags }
            )
            .distinct()
            .map { SearchCategory(it) }
            .filter { category -> matchScore(normalizedQuery, category.label) > 0 }
            .take(8)

        return SearchResults(
            merchants = merchantResults,
            dishes = dishResults,
            categories = categoryResults
        )
    }

    private fun matchScore(query: String, vararg values: String): Int {
        val normalizedValues = values
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { it.lowercase() }

        var bestScore = 0
        normalizedValues.forEach { value ->
            when {
                value == query -> bestScore = maxOf(bestScore, 100)
                value.startsWith(query) -> bestScore = maxOf(bestScore, 75)
                value.contains(query) -> bestScore = maxOf(bestScore, 55)
                value.split(Regex("[^a-z0-9]+")).any { token -> token.startsWith(query) } ->
                    bestScore = maxOf(bestScore, 35)
            }
        }
        return bestScore
    }

    private fun normalizeQuery(query: String): String {
        return when (query.trim().lowercase()) {
            "麦当劳" -> "mcdonald"
            "薯饼" -> "hash browns"
            "双吉" -> "double cheeseburger"
            else -> query.trim().lowercase()
        }
    }
}
