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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.example.ubereats_sim.LocalFavorites
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
            "🍽️" to "Dine Out",
            "🍱" to "Browse",
            "🍣" to "Sushi",
            "🏀" to "Game Day",
            "🍕" to "Pizza"
        )
        items(categories) { (emoji, label) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(56.dp)
                    .clickable { nav(label) }
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(emoji, fontSize = 22.sp)
                }
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
        items(listOf("⭕ Uber One", "🚶 Pickup", "🏷️ Offers", "⏱️ 30 min")) { filter ->
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFF5F5F5),
                modifier = Modifier.clickable { nav(filter.substringAfter(" ")) }
            ) {
                Text(
                    filter,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { nav(title) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
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
                if (row.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Restaurant, modifier: Modifier = Modifier) {
    val nav = LocalNavController.current
    val favorites = LocalFavorites.current
    val isFavorite = favorites.first.contains(restaurant.name)
    val merchantImage = rememberMerchantImage(restaurant.name, reqWidth = 640, reqHeight = 360)
    Card(
        modifier = modifier.clickable { nav(restaurant.name) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFFE0E0E0))
            ) {
                if (merchantImage != null) {
                    Image(
                        bitmap = merchantImage,
                        contentDescription = restaurant.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                if (restaurant.discount != null) {
                    Surface(color = Color(0xFF05944F), modifier = Modifier.align(Alignment.TopStart)) {
                        Text(
                            restaurant.discount,
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        restaurant.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { favorites.second(restaurant.name) }
                    )
                }
                Text(
                    "${restaurant.deliveryFee} · ${restaurant.deliveryTime}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { store ->
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
                            Text(storeEmojis[store.icon] ?: "🏬", fontSize = 24.sp)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            store.name,
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { nav("Promotions") },
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF05944F)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Get 20% off orders over US$40",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text("(Up to US$15) Walgreens", color = Color.White, fontSize = 12.sp)
            }
            Text("🛍", fontSize = 32.sp)
        }
    }
}
