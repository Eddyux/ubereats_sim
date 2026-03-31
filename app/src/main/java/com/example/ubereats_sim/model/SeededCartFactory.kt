package com.example.ubereats_sim.model

import android.content.Context
import com.example.ubereats_sim.presenter.MerchantPresenter

object SeededCartFactory {
    fun build(context: Context, merchantPresenter: MerchantPresenter): List<MerchantCartItem> {
        return DataLoader.loadCart(context).flatMap { cartItem ->
            val products = merchantPresenter.getMerchantProducts(cartItem.restaurantName)
            if (products.isEmpty()) {
                emptyList()
            } else {
                List(cartItem.itemCount) { index ->
                    MerchantCartItem(
                        merchantName = cartItem.restaurantName,
                        product = products[index % products.size],
                        quantity = 1
                    )
                }
            }
        }
    }
}
