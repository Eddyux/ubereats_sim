package com.example.ubereats_sim.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DataLoader {
    private val gson = Gson()
    private const val TAG = "DataLoader"

    fun loadRestaurants(context: Context): List<Restaurant> {
        return loadRestaurantList(context, "data/restaurants.json", "restaurants")
    }

    fun loadGroceryMerchants(context: Context): List<Restaurant> {
        return loadRestaurantList(context, "data/grocery_merchants.json", "groceryMerchants")
    }

    fun loadConvenienceMerchants(context: Context): List<Restaurant> {
        return loadRestaurantList(context, "data/convenience_merchants.json", "convenienceMerchants")
    }

    private fun loadRestaurantList(context: Context, assetPath: String, key: String): List<Restaurant> {
        return try {
            val json = context.assets.open(assetPath)
                .bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, Any>>() {}.type
            val data: Map<String, Any> = gson.fromJson(json, type)
            val listJson = gson.toJson(data[key])
            val listType = object : TypeToken<List<Restaurant>>() {}.type
            gson.fromJson(listJson, listType) ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Error loading restaurant list from $assetPath", e)
            emptyList()
        }
    }

    fun loadNearbyStores(context: Context): List<NearbyStore> {
        return try {
            val json = context.assets.open("data/restaurants.json")
                .bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, Any>>() {}.type
            val data: Map<String, Any> = gson.fromJson(json, type)
            val listJson = gson.toJson(data["nearbyStores"])
            val listType = object : TypeToken<List<NearbyStore>>() {}.type
            gson.fromJson(listJson, listType) ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Error loading nearby stores", e)
            emptyList()
        }
    }

    fun loadUserProfile(context: Context): UserProfile {
        return try {
            val json = context.assets.open("data/user_profile.json")
                .bufferedReader().use { it.readText() }
            gson.fromJson(json, UserProfile::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading user profile", e)
            UserProfile("User", false, null)
        }
    }

    fun loadCart(context: Context): List<CartItem> {
        return try {
            val json = context.assets.open("data/cart.json")
                .bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, List<CartItem>>>() {}.type
            val data: Map<String, List<CartItem>> = gson.fromJson(json, type)
            data["cartItems"] ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Error loading cart", e)
            emptyList()
        }
    }

    fun loadMerchantProductSeeds(context: Context): Map<String, List<MerchantProductSeed>> {
        return try {
            val assetDir = "data/merchant_products"
            val files = context.assets.list(assetDir)?.filter { it.endsWith(".json") } ?: emptyList()
            val seedMap = mutableMapOf<String, List<MerchantProductSeed>>()

            for (fileName in files) {
                val json = context.assets.open("$assetDir/$fileName")
                    .bufferedReader().use { it.readText() }
                val group = gson.fromJson(json, MerchantProductSeedGroup::class.java)
                val merchantName = group.merchantName?.trim().orEmpty()
                val products = group.products ?: emptyList()
                if (merchantName.isNotEmpty() && products.isNotEmpty()) {
                    seedMap[merchantName] = products
                }
            }
            seedMap
        } catch (e: Exception) {
            Log.e(TAG, "Error loading merchant product seeds", e)
            emptyMap()
        }
    }

    fun loadOrders(context: Context): List<Order> {
        return try {
            val json = context.assets.open("data/orders.json")
                .bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, List<Order>>>() {}.type
            val data: Map<String, List<Order>> = gson.fromJson(json, type)
            data["orders"] ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Error loading orders", e)
            emptyList()
        }
    }
}
