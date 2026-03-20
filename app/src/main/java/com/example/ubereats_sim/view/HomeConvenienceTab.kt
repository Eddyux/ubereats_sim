package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ubereats_sim.model.Restaurant

@Composable
fun ConvenienceTabContent(convenienceMerchants: List<Restaurant>) {
    val topList = convenienceMerchants.take(6)
    val moreList = if (convenienceMerchants.size > 6) convenienceMerchants.drop(2) else convenienceMerchants

    LazyColumn(modifier = Modifier.background(Color.White)) {
        item { SectionTitle("Convenience stores near you") }
        item { RestaurantGrid(topList) }
        item { SectionTitle("Quick essentials") }
        item { RestaurantGrid(moreList) }
        item { PromoBanner() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}
