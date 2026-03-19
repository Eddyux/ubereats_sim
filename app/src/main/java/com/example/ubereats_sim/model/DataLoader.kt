package com.example.ubereats_sim.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DataLoader {
    private val gson = Gson()
    private const val TAG = "DataLoader"

    fun loadRestaurants(context: Context): List<Restaurant> {
        return try {
            val json = context.assets.open("data/restaurants.json")
                .bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, Any>>() {}.type
            val data: Map<String, Any> = gson.fromJson(json, type)
            val listJson = gson.toJson(data["restaurants"])
            val listType = object : TypeToken<List<Restaurant>>() {}.type
            gson.fromJson(listJson, listType) ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Error loading restaurants", e)
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
            UserProfile("用户", false, null)
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
}
