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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.model.MerchantCartItem

@Composable
fun ViewCartScreen(
    merchantName: String,
    items: List<MerchantCartItem>,
    onClose: () -> Unit,
    onRemove: (MerchantCartItem) -> Unit,
    onReplace: (MerchantCartItem) -> Unit,
    onAddItems: () -> Unit,
    onOpenOfferItem: () -> Unit,
    onCheckout: () -> Unit = {}
) {
    val utensilChecked = remember { mutableStateOf(false) }
    val subtotal = items.sumOf { it.quantity * it.product.price }
    val originalTotal = subtotal + 4.59

    androidx.compose.material3.Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF9B6A00))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Saving $8.98 with Uber One and promotions", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Text(
                    "Add $9.22 to save $5 more with promotions",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .background(Color(0xFF9B6A00))
                        .padding(start = 14.dp, end = 14.dp, bottom = 12.dp)
                )
                Button(
                    onClick = { onCheckout() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text("Go to checkout", color = Color.White, fontSize = 18.sp, modifier = Modifier.padding(vertical = 6.dp))
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.clickable { onClose() })
                    Icon(Icons.Default.Person, contentDescription = "Invite")
                }
                Text(merchantName, fontSize = 38.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp))
            }

            items(items) { item ->
                ViewCartItemRow(item = item, onRemove = { onRemove(item) }, onReplace = { onReplace(item) })
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        color = Color(0xFFF2F2F2),
                        shape = RoundedCornerShape(22.dp),
                        modifier = Modifier.clickable { onAddItems() }
                    ) {
                        Text("+ Add items", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp))
                    }
                }
                HorizontalDivider(thickness = 0.7.dp, color = Color(0xFFE8E8E8))
            }

            item {
                Text("Offers for you", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
                OfferCard(onOpen = onOpenOfferItem)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Request utensils, etc.", fontSize = 16.sp)
                    Checkbox(checked = utensilChecked.value, onCheckedChange = { utensilChecked.value = it })
                }
                HorizontalDivider(thickness = 0.7.dp, color = Color(0xFFE8E8E8))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Subtotal", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "US$${String.format("%.2f", originalTotal)}",
                            style = TextStyle(textDecoration = TextDecoration.LineThrough),
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("US$${String.format("%.2f", subtotal)}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ViewCartItemRow(item: MerchantCartItem, onRemove: () -> Unit, onReplace: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .background(Color(0xFFEDEDED), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("🍔", fontSize = 26.sp)
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(item.product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("This item is unavailable", color = Color(0xFFB00020), fontSize = 14.sp)
            Text(item.product.description.ifBlank { item.product.name }, color = Color(0xFF555555), fontSize = 14.sp)
            Text(item.product.priceText, fontSize = 15.sp, modifier = Modifier.padding(top = 4.dp))
            if (item.product.badgeText != null) {
                Text(
                    item.product.badgeText,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(Color.Red, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.Black,
                    modifier = Modifier.clickable { onReplace() }
                ) {
                    Text("Replace", color = Color.White, modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp))
                }
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFF2F2F2),
                    modifier = Modifier.clickable { onRemove() }
                ) {
                    Text("Remove", modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
private fun OfferCard(onOpen: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF9F9F9),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onOpen() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Medium French Fries", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text("US$5.49", fontSize = 15.sp)
                Text(
                    "Buy 1, get 1 free",
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(Color.Red, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Surface(shape = CircleShape, color = Color.White, shadowElevation = 2.dp) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.padding(6.dp))
            }
        }
    }
}
