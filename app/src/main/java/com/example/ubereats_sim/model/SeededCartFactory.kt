package com.example.ubereats_sim.model

import android.content.Context
import com.example.ubereats_sim.presenter.MerchantPresenter

object SeededCartFactory {
    fun build(context: Context, merchantPresenter: MerchantPresenter): List<MerchantCartItem> {
        return DataLoader.loadCart(context).flatMap { cartItem ->
            val products = merchantPresenter.getMerchantProducts(cartItem.restaurantName)
            if (products.isEmpty() || cartItem.itemCount <= 0) {
                emptyList()
            } else {
                val selectedProducts = List(cartItem.itemCount) { index ->
                    products[index % products.size]
                }
                val seededPrices = buildSeededPrices(
                    totalPrice = cartItem.totalPrice,
                    itemCount = cartItem.itemCount
                )

                selectedProducts.mapIndexed { index, product ->
                    MerchantCartItem(
                        merchantName = cartItem.restaurantName,
                        product = product.copy(
                            price = seededPrices[index],
                            priceText = "US$${String.format("%.2f", seededPrices[index])}"
                        ),
                        quantity = 1
                    )
                }
            }
        }
    }

    private fun buildSeededPrices(totalPrice: Double, itemCount: Int): List<Double> {
        val totalCents = (totalPrice * 100).toInt()
        val baseCents = totalCents / itemCount
        val remainder = totalCents % itemCount

        return List(itemCount) { index ->
            val cents = baseCents + if (index < remainder) 1 else 0
            cents / 100.0
        }
    }
}
