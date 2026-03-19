package com.example.ubereats_sim.presenter

import android.content.Context
import com.example.ubereats_sim.model.DataLoader
import com.example.ubereats_sim.model.NearbyStore
import com.example.ubereats_sim.model.Restaurant

class HomePresenter(private val context: Context) {

    fun getRestaurants(): List<Restaurant> {
        return DataLoader.loadRestaurants(context)
    }

    fun getRecommendedRestaurants(): List<Restaurant> {
        return getRestaurants().filter { it.section == "recommended" }
    }

    fun getAffordableRestaurants(): List<Restaurant> {
        return getRestaurants().filter { it.section == "affordable" }
    }

    fun getFastDeliveryRestaurants(): List<Restaurant> {
        return getRestaurants().filter { it.section == "fast_delivery" }
    }

    fun getPopularNearbyRestaurants(): List<Restaurant> {
        return getRestaurants().filter { it.section == "popular_nearby" }
    }

    fun getNearbyStores(): List<NearbyStore> {
        return DataLoader.loadNearbyStores(context)
    }
}
