package com.example.ubereats_sim.presenter

import android.content.Context
import com.example.ubereats_sim.model.CartItem
import com.example.ubereats_sim.model.DataLoader

class CartPresenter(private val context: Context) {

    fun getCartItems(): List<CartItem> {
        return DataLoader.loadCart(context)
    }

    fun getTotalItemCount(): Int {
        return getCartItems().sumOf { it.itemCount }
    }
}
