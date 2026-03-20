package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.model.MerchantMenuItem
import com.example.ubereats_sim.presenter.MerchantPresenter

@Composable
fun MerchantScreen(
    restaurantName: String,
    cartCount: Int = 0,
    onOpenProduct: (MerchantMenuItem) -> Unit = {},
    onOpenCart: () -> Unit = {}
) {
    val nav = LocalNavController.current
    val onBack = LocalNavBack.current
    val context = LocalContext.current
    val presenter = remember(context) { MerchantPresenter(context) }
    val summary = presenter.getMerchantSummary(restaurantName)
    val allProducts = presenter.getMerchantProducts(restaurantName)

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                MerchantHero(
                    merchantName = summary.name,
                    onBack = onBack,
                    onSearch = { nav("Search in ${summary.name}") },
                    onFavorite = { nav("Favorite ${summary.name}") },
                    onMore = { nav("More options") }
                )
            }
            item {
                MerchantHeader(summary)
            }

            items(allProducts.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { item ->
                        MenuItemCard(
                            merchantName = restaurantName,
                            item = item,
                            modifier = Modifier.weight(1f),
                            onAdd = { onOpenProduct(item) },
                            onOpen = { onOpenProduct(item) }
                        )
                    }
                    if (rowItems.size == 1) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }

            item { Spacer(Modifier.height(88.dp)) }
        }

        if (cartCount > 0) {
            Surface(
                color = Color.Black,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .clickable { onOpenCart() }
            ) {
                Text(
                    "view cart $cartCount",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun MerchantHeader(summary: com.example.ubereats_sim.model.MerchantSummary) {
    val nav = LocalNavController.current
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        MerchantBrand(summary)
        MerchantActionRow(
            onDelivery = { nav("Delivery") },
            onPickup = { nav("Pickup") },
            onGroupOrder = { nav("Group order") }
        )
        Spacer(Modifier.height(14.dp))
        DeliveryInfoRow(summary.deliveryFeeText, summary.prepTimeText, summary.subtitle)
        Spacer(Modifier.height(14.dp))
        PromoCard { nav("$5 off $20+") }
    }
}
