package com.example.ubereats_sim.presenter

import android.content.Context
import com.example.ubereats_sim.model.DataLoader
import com.example.ubereats_sim.model.Order

class OrderPresenter(private val context: Context) {
    fun getOrders(): List<Order> {
        return DataLoader.loadOrders(context)
    }
}
