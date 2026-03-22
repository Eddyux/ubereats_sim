package com.example.ubereats_sim.presenter

import android.content.Context
import com.example.ubereats_sim.model.DataLoader
import com.example.ubereats_sim.model.MerchantMenuItem
import com.example.ubereats_sim.model.MerchantProductSeed
import com.example.ubereats_sim.model.MerchantSummary
import com.example.ubereats_sim.model.ProductOption

class MerchantPresenter(private val context: Context) {

    fun getMerchantSummary(restaurantName: String): MerchantSummary {
        return MerchantSummary(
            name = restaurantName,
            rating = when (restaurantName) {
                "Matchaful" -> 4.8
                "VINEYARD" -> 4.7
                "Benvenuto Cafe" -> 4.6
                "McDonald's" -> 4.6
                else -> 4.4
            },
            reviewCountText = "(5,000+)",
            badge = "Uber One",
            distanceText = "0.5 mi",
            prepTimeText = "15 min",
            deliveryFeeText = "US$0 Delivery Fee on US$15+",
            subtitle = "Other fees may apply"
        )
    }

    fun getMerchantProducts(restaurantName: String): List<MerchantMenuItem> {
        val seedMap = DataLoader.loadMerchantProductSeeds(context)
        val seeds = (seedMap[restaurantName] ?: defaultSeeds(restaurantName)).take(16)
        return seeds.mapIndexed { index, seed ->
            val safeName = seed.name?.takeIf { it.isNotBlank() } ?: "Item ${index + 1}"
            val safePrice = seed.price ?: 0.0
            val safeCalorie = seed.calorieText ?: ""
            MerchantMenuItem(
                id = "${restaurantName.lowercase().replace(" ", "_")}_${index + 1}",
                name = safeName,
                description = seed.description ?: "",
                price = safePrice,
                priceText = formatPrice(safePrice),
                calorieText = safeCalorie,
                badgeText = seed.badgeText,
                options = optionsFor(restaurantName, seed)
            )
        }
    }

    fun getProductById(restaurantName: String, productId: String): MerchantMenuItem? {
        return getMerchantProducts(restaurantName).firstOrNull { it.id == productId }
    }

    data class MerchantInfo(
        val rating: String,
        val deliveryFee: String,
        val eta: String,
        val deal: String?
    )

    fun getMerchantInfo(restaurantName: String): MerchantInfo? {
        val summary = getMerchantSummary(restaurantName)
        return MerchantInfo(
            rating = summary.rating.toString(),
            deliveryFee = summary.deliveryFeeText,
            eta = summary.prepTimeText,
            deal = null
        )
    }

    private fun optionsFor(merchantName: String, seed: MerchantProductSeed): List<ProductOption> {
        val seedName = seed.name.orEmpty()
        if (merchantName == "McDonald's" && seedName == "Steak & Egg McMuffin") {
            return listOf(
                ProductOption("Butter", 0.23, "35 Cal."),
                ProductOption("American Cheese", 1.29, "50 Cal."),
                ProductOption("Round Egg", 2.79, "80 Cal."),
                ProductOption("Slivered Onions", 0.25, "5 Cal."),
                ProductOption("Steak", 3.19, "160 Cal."),
                ProductOption("English McMuffin", 1.99, "160 Cal.")
            )
        }

        val name = seedName.lowercase()
        return when {
            name.contains("latte") || name.contains("smoothie") || name.contains("juice") || name.contains("tea") || name.contains("slurpee") -> listOf(
                ProductOption("Less ice", 0.00, "0 Cal."),
                ProductOption("50% sweetness", 0.00, "0 Cal."),
                ProductOption("Extra shot", 1.20, "20 Cal.")
            )

            name.contains("pizza") -> listOf(
                ProductOption("Thin crust", 0.00, "0 Cal."),
                ProductOption("Extra cheese", 1.50, "90 Cal."),
                ProductOption("Pepperoni topping", 1.80, "110 Cal.")
            )

            name.contains("noodle") || name.contains("rice noodle") -> listOf(
                ProductOption("Mild spice", 0.00, "0 Cal."),
                ProductOption("Add boiled egg", 1.30, "70 Cal."),
                ProductOption("Extra beef", 2.80, "150 Cal.")
            )

            name.contains("burger") || name.contains("mcmuffin") || name.contains("whopper") || name.contains("sandwich") -> listOf(
                ProductOption("No onions", 0.00, "0 Cal."),
                ProductOption("Add cheese", 1.29, "60 Cal."),
                ProductOption("Extra patty", 2.99, "180 Cal.")
            )

            name.contains("fries") || name.contains("hash browns") || name.contains("nuggets") -> listOf(
                ProductOption("No salt", 0.00, "0 Cal."),
                ProductOption("Spicy seasoning", 0.50, "15 Cal."),
                ProductOption("Extra dip sauce", 0.80, "40 Cal.")
            )

            else -> listOf(
                ProductOption("Customize sauce", 0.50, "10 Cal."),
                ProductOption("Add cheese", 1.29, "60 Cal."),
                ProductOption("Extra protein", 2.99, "140 Cal.")
            )
        }
    }

    private fun formatPrice(price: Double): String {
        return "US$${String.format("%.2f", price)}"
    }

    private fun defaultSeeds(restaurantName: String): List<MerchantProductSeed> {
        return listOf(
            MerchantProductSeed("$restaurantName Signature Bowl", 12.99, "520 Cal."),
            MerchantProductSeed("$restaurantName Combo", 14.99, "780 Cal."),
            MerchantProductSeed("$restaurantName Special", 10.99, "460 Cal."),
            MerchantProductSeed("$restaurantName Family Pack", 24.99, "1200 Cal.")
        )
    }
}
