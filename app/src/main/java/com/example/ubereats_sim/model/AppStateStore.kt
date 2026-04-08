package com.example.ubereats_sim.model

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import java.io.File

object AppStateStore {
    private const val fileName = "app_state.json"
    private const val tag = "AppStateStore"
    private val gson = GsonBuilder().setPrettyPrinting().create()

    data class PersistedAppState(
        val cartItems: List<PersistedCartItem> = emptyList(),
        val orders: List<Order> = emptyList()
    )

    data class PersistedCartItem(
        val merchantName: String,
        val productId: String,
        val unitPrice: Double? = null,
        val quantity: Int,
        val selectedOptions: Map<String, Int> = emptyMap()
    )

    data class RestoredAppState(
        val cartItems: List<MerchantCartItem>,
        val orders: List<Order>
    )

    fun restore(
        context: Context,
        defaultCartItems: List<MerchantCartItem>,
        defaultOrders: List<Order>
    ): RestoredAppState {
        val persistedState = readPersistedState(context) ?: return RestoredAppState(
            cartItems = defaultCartItems,
            orders = defaultOrders
        )

        return RestoredAppState(
            // Each cold start should begin from the seeded cart so repeated runs are deterministic.
            cartItems = defaultCartItems,
            orders = persistedState.orders
        )
    }

    fun save(
        context: Context,
        cartItems: List<MerchantCartItem>,
        orders: List<Order>
    ) {
        runCatching {
            val file = File(context.filesDir, fileName)
            val state = PersistedAppState(
                cartItems = cartItems.map { item ->
                    PersistedCartItem(
                        merchantName = item.merchantName,
                        productId = item.product.id,
                        unitPrice = item.product.price,
                        quantity = item.quantity,
                        selectedOptions = item.selectedOptions
                    )
                },
                orders = orders
            )
            file.writeText(gson.toJson(state))
        }.onFailure {
            Log.e(tag, "Failed to persist app state", it)
        }
    }

    private fun readPersistedState(context: Context): PersistedAppState? {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) {
            return null
        }

        val content = runCatching { file.readText() }
            .onFailure { Log.e(tag, "Failed to read persisted app state", it) }
            .getOrNull()
            ?.trim()
            .orEmpty()

        if (content.isBlank()) {
            return null
        }

        return runCatching {
            gson.fromJson(content, PersistedAppState::class.java)
        }.onFailure {
            Log.e(tag, "Failed to parse persisted app state", it)
        }.getOrNull()
    }
}
