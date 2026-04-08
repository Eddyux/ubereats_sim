package com.example.ubereats_sim.model

import android.content.Context
import android.util.Log
import com.example.ubereats_sim.presenter.MerchantPresenter
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
        merchantPresenter: MerchantPresenter,
        defaultCartItems: List<MerchantCartItem>,
        defaultOrders: List<Order>
    ): RestoredAppState {
        val persistedState = readPersistedState(context) ?: return RestoredAppState(
            cartItems = defaultCartItems,
            orders = defaultOrders
        )

        return RestoredAppState(
            cartItems = restoreCartItems(
                persistedItems = persistedState.cartItems,
                merchantPresenter = merchantPresenter,
                seededCartSummaries = DataLoader.loadCart(context).associateBy { it.restaurantName }
            ),
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

    private fun restoreCartItems(
        persistedItems: List<PersistedCartItem>,
        merchantPresenter: MerchantPresenter,
        seededCartSummaries: Map<String, CartItem>
    ): List<MerchantCartItem> {
        return persistedItems
            .groupBy { it.merchantName }
            .flatMap { (merchantName, merchantItems) ->
                val migratedPrices = buildMissingPriceMap(
                    merchantItems = merchantItems,
                    seededSummary = seededCartSummaries[merchantName]
                )

                merchantItems.mapNotNull { item ->
                    val product = merchantPresenter.getProductById(item.merchantName, item.productId)
                    if (product == null) {
                        Log.w(tag, "Missing product seed for ${item.merchantName}/${item.productId}")
                        null
                    } else {
                        val restoredPrice = item.unitPrice ?: migratedPrices[item] ?: product.price
                        MerchantCartItem(
                            merchantName = item.merchantName,
                            product = product.copy(
                                price = restoredPrice,
                                priceText = "US$${String.format("%.2f", restoredPrice)}"
                            ),
                            quantity = item.quantity,
                            selectedOptions = item.selectedOptions
                        )
                    }
                }
            }
    }

    private fun buildMissingPriceMap(
        merchantItems: List<PersistedCartItem>,
        seededSummary: CartItem?
    ): Map<PersistedCartItem, Double> {
        val itemsMissingPrice = merchantItems.filter { it.unitPrice == null }
        if (itemsMissingPrice.isEmpty() || seededSummary == null) {
            return emptyMap()
        }

        val knownCents = merchantItems
            .mapNotNull { it.unitPrice }
            .sumOf { (it * 100).toInt() }
        val remainingCents = ((seededSummary.totalPrice * 100).toInt() - knownCents).coerceAtLeast(0)
        val baseCents = remainingCents / itemsMissingPrice.size
        val remainder = remainingCents % itemsMissingPrice.size

        return itemsMissingPrice.mapIndexed { index, item ->
            val cents = baseCents + if (index < remainder) 1 else 0
            item to (cents / 100.0)
        }.toMap()
    }
}
