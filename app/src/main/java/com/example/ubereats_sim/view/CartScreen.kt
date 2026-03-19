package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.model.CartItem
import com.example.ubereats_sim.presenter.CartPresenter

@Composable
fun CartScreen() {
    val context = LocalContext.current
    val presenter = CartPresenter(context)
    val cartItems = presenter.getCartItems()

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        CartHeader()
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cartItems) { cartItem -> CartItemCard(cartItem) }
        }
    }
}

@Composable
private fun CartHeader() {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("购物车", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Surface(
            shape = RoundedCornerShape(20.dp), color = Color(0xFFF5F5F5),
            modifier = Modifier.clickable { nav("订单") }
        ) {
            Text("订单", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), fontSize = 14.sp)
        }
    }
}

@Composable
private fun CartItemCard(cartItem: CartItem) {
    val nav = LocalNavController.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFE0E0E0)))
                    Column {
                        Text(cartItem.restaurantName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("${cartItem.itemCount} 项商品 · US$${String.format("%.2f", cartItem.totalPrice)}",
                            fontSize = 14.sp, color = Color.Gray)
                        Text("派送地址：${cartItem.deliveryAddress}", fontSize = 14.sp, color = Color.Gray)
                    }
                }
                Icon(Icons.Default.MoreVert, null, Modifier.size(24.dp))
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { nav("查看购物车 - ${cartItem.restaurantName}") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(8.dp)
            ) { Text("查看购物车", Modifier.padding(vertical = 8.dp)) }
            Spacer(Modifier.height(8.dp))
            OutlinedButton(
                onClick = { nav(cartItem.restaurantName) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) { Text("查看店铺", Modifier.padding(vertical = 8.dp), color = Color.Black) }
        }
    }
}
