package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.model.NearbyStore
import com.example.ubereats_sim.model.Restaurant

@Composable
fun GroceryTabContent(
    nearbyStores: List<NearbyStore>,
    featuredRestaurants: List<Restaurant>,
    featuredDeals: List<Restaurant>
) {
    LazyColumn(modifier = Modifier.background(Color.White)) {
        item { GroceryActionRow() }
        item { SectionTitle("Stores near you") }
        item { GroceryStoreGrid(nearbyStores) }
        item { SectionTitle("Featured on Uber Eats") }
        item { RestaurantGrid(featuredRestaurants) }
        item { SectionTitle("Featured deals") }
        item { GroceryDealsRow(featuredDeals) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
private fun GroceryActionRow() {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GroceryActionCard("🏷", "Offers", Modifier.weight(1f)) { nav("Offers") }
        GroceryActionCard("📝", "My list", Modifier.weight(1f)) { nav("My list") }
    }
}

@Composable
private fun GroceryActionCard(
    emoji: String,
    label: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(emoji, fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text(label, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
private fun GroceryStoreGrid(stores: List<NearbyStore>) {
    val nav = LocalNavController.current
    val notes = listOf("10 min", "15 min", "20% off", "Buy 1, get 1")
    val icons = mapOf(
        "target" to "🎯",
        "cvs" to "🏪",
        "eataly" to "🛒",
        "walgreens" to "💊",
        "seveneleven" to "🏧",
        "gopuff" to "📦",
        "morton" to "🥩",
        "ubereats" to "🍽"
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        stores.chunked(4).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEachIndexed { index, store ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                            .clickable { nav(store.name) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(icons[store.icon] ?: "🏬", fontSize = 24.sp)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            store.name,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            notes[(index + store.id.length) % notes.size],
                            fontSize = 10.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun GroceryDealsRow(restaurants: List<Restaurant>) {
    val nav = LocalNavController.current
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(restaurants.take(6)) { index, restaurant ->
            val merchantImage = rememberMerchantImage(restaurant.name, reqWidth = 600, reqHeight = 320)
            Card(
                modifier = Modifier
                    .width(170.dp)
                    .clickable { nav(restaurant.name) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .background(Color(0xFFE7E7E7))
                ) {
                    if (merchantImage != null) {
                        Image(
                            bitmap = merchantImage,
                            contentDescription = restaurant.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text(
                        restaurant.discount ?: "Deal",
                        color = Color.White,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .padding(6.dp)
                            .background(Color(0xFF05944F), RoundedCornerShape(8.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(restaurant.name, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)
                    Text("From US$${9 + index}.99", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}
