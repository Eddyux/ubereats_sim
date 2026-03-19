package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.model.NearbyStore
import com.example.ubereats_sim.model.Restaurant

@Composable
fun CategoryRow() {
    val nav = LocalNavController.current
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        val categories = listOf(
            "🍽️" to "外出就餐", "🍱" to "浏览", "🍣" to "寿司",
            "🏀" to "球赛日", "🍕" to "披萨"
        )
        items(categories) { (emoji, label) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(56.dp).clickable { nav(label) }
            ) {
                Box(
                    modifier = Modifier.size(48.dp).clip(CircleShape)
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) { Text(emoji, fontSize = 22.sp) }
                Spacer(Modifier.height(4.dp))
                Text(label, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun FilterRow() {
    val nav = LocalNavController.current
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(listOf("⭕ Uber One", "🚶 自取", "🏷️ 优惠", "⏱️ 30分")) { filter ->
            Surface(
                shape = RoundedCornerShape(20.dp), color = Color(0xFFF5F5F5),
                modifier = Modifier.clickable { nav(filter.substringAfter(" ")) }
            ) {
                Text(filter, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { nav(title) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(20.dp))
    }
}

@Composable
fun RestaurantGrid(restaurants: List<Restaurant>) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        restaurants.chunked(2).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEach { restaurant ->
                    RestaurantCard(restaurant, Modifier.weight(1f))
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Restaurant, modifier: Modifier = Modifier) {
    val nav = LocalNavController.current
    Card(
        modifier = modifier.clickable { nav(restaurant.name) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(120.dp).background(Color(0xFFE0E0E0))) {
                if (restaurant.discount != null) {
                    Surface(color = Color(0xFF05944F), modifier = Modifier.align(Alignment.TopStart)) {
                        Text(restaurant.discount, color = Color.White, fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(restaurant.name, fontWeight = FontWeight.Bold, fontSize = 14.sp,
                    maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("${restaurant.deliveryFee} · ${restaurant.deliveryTime}",
                    fontSize = 12.sp, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⭐ ${restaurant.rating}", fontSize = 12.sp)
                    Text(" (${restaurant.reviewCount}+)", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun NearbyStoreGrid(stores: List<NearbyStore>) {
    val nav = LocalNavController.current
    val storeEmojis = mapOf(
        "target" to "🎯", "cvs" to "💊", "eataly" to "🍝", "walgreens" to "💚",
        "seveneleven" to "🏪", "gopuff" to "📦", "morton" to "🥩", "ubereats" to "🍔"
    )
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        stores.chunked(4).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { store ->
                    Column(
                        modifier = Modifier.weight(1f).padding(vertical = 8.dp)
                            .clickable { nav(store.name) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(56.dp).clip(CircleShape).background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) { Text(storeEmojis[store.icon] ?: "🏬", fontSize = 24.sp) }
                        Spacer(Modifier.height(4.dp))
                        Text(store.name, fontSize = 11.sp, maxLines = 1,
                            overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
                    }
                }
                repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
fun PromoBanner() {
    val nav = LocalNavController.current
    Surface(
        modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { nav("优惠活动") },
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF05944F)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("消费满 US$40 可享 8 折优惠", color = Color.White,
                    fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("(最高优惠 US$15) Walgreens", color = Color.White, fontSize = 12.sp)
            }
            Text("🛒", fontSize = 32.sp)
        }
    }
}
