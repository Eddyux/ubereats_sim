package com.example.ubereats_sim.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.LocalOrders
import com.example.ubereats_sim.model.Order

@Composable
fun OrdersScreen() {
    val orders = LocalOrders.current
    val navController = LocalNavController.current
    val navBack = LocalNavBack.current
    var selectedTab by remember { mutableStateOf(1) }

    val activeOrders = orders.filter { it.status == "In Progress" }
    val historyOrders = orders.filter { it.status != "In Progress" }

    val pastPurchaseItems = remember(historyOrders) {
        historyOrders.flatMap { order ->
            order.items.map { item ->
                PastPurchaseEntry(item.name, order.merchantName, item.price)
            }
        }.distinctBy { "${it.merchantName}|${it.itemName}" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("Orders", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Past purchases") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Past orders") }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (selectedTab == 0) {
                items(pastPurchaseItems) { entry ->
                    PastPurchaseItemCard(entry, navController)
                }
            }

            if (selectedTab == 1) {
                if (activeOrders.isNotEmpty()) {
                    item {
                        Text(
                            "Active",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    items(activeOrders) { order ->
                        ActiveOrderCard(order, navController)
                    }
                }

                if (historyOrders.isNotEmpty()) {
                    item {
                        Text(
                            "History",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    items(historyOrders) { order ->
                        HistoryOrderCard(order, navController)
                    }
                }
            }
        }
    }
}

private data class PastPurchaseEntry(
    val itemName: String,
    val merchantName: String,
    val price: Double
)

@Composable
private fun PastPurchaseItemCard(entry: PastPurchaseEntry, navController: (String) -> Unit) {
    val productImage = rememberMerchantProductImage(entry.merchantName, entry.itemName, 200, 200)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController("merchant|${entry.merchantName}") }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            if (productImage != null) {
                Image(
                    bitmap = productImage,
                    contentDescription = entry.itemName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("🍽", fontSize = 28.sp)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(entry.itemName, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(entry.merchantName, fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                "$${String.format("%.2f", entry.price)}",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        Button(
            onClick = { navController("merchant|${entry.merchantName}") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text("Add", color = Color.Black, fontSize = 13.sp)
        }
    }
}

@Composable
private fun ActiveOrderCard(order: Order, navController: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController("order_detail/${order.id}") },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2D5F2E)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(order.merchantLogo, fontSize = 28.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        order.merchantName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    order.promoMessage?.let {
                        Text(it, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "${order.items.size} items • $${String.format("%.2f", order.totalAmount)}",
                fontSize = 14.sp,
                color = Color.White
            )
            order.estimatedArrival?.let {
                Text(
                    "Estimated arrival $it",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { navController("order_detail/${order.id}") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Track", color = Color(0xFF2D5F2E), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun HistoryOrderCard(order: Order, navController: (String) -> Unit) {
    val merchantImage = rememberMerchantImage(order.merchantName, 120, 120)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController("order_history_detail/${order.id}") }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            if (merchantImage != null) {
                Image(
                    bitmap = merchantImage,
                    contentDescription = order.merchantName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(order.merchantLogo, fontSize = 32.sp)
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                order.merchantName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "${order.orderDate} • $${String.format("%.2f", order.totalAmount)}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "${order.items.size} items",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Button(
            onClick = { navController("merchant|${order.merchantName}") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("View store", color = Color.Black, fontSize = 12.sp)
        }
    }
}
