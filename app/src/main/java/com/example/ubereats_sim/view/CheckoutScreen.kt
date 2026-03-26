package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.model.MerchantCartItem

@Composable
fun CheckoutScreen(
    merchantName: String = "",
    cartItems: List<MerchantCartItem> = emptyList(),
    onNext: () -> Unit = {}
) {
    val navBack = LocalNavBack.current
    val navController = LocalNavController.current

    val subtotal = cartItems.sumOf { it.quantity * it.product.price }
    val deliveryFee = if (subtotal >= 15.0) 0.0 else 2.99
    val taxesAndFees = (subtotal * 0.08875).let { (it * 100).toInt() / 100.0 }
    val total = subtotal + deliveryFee + taxesAndFees
    val itemCount = cartItems.sumOf { it.quantity }
    var selectedDeliveryTime by remember { mutableStateOf("Standard") }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            Column(modifier = Modifier.background(Color.White)) {
                // Savings banner
                if (deliveryFee == 0.0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(Color(0xFF9B6A00), RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "You're saving on delivery with this order",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Button(
                    onClick = { onNext() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "Next",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
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
            // Top bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Checkout", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Map area with delivery address
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color(0xFFE8E8E8))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFD4E8D4)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("New York, NY", fontSize = 14.sp, color = Color.DarkGray)
                        }
                    }
                }
            }

            // Meet at my door
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Meet at my door", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Text("123 Main St, New York, NY", fontSize = 14.sp, color = Color.Gray)
                    }
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
            }

            // Phone contact
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("+1 (555) 000-0000", fontSize = 16.sp)
                }
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
            }

            // Delivery time
            item {
                Text(
                    "Delivery time",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DeliveryTimeChip(
                        label = "Standard",
                        subtitle = "11:50 AM-12:06 PM",
                        isSelected = selectedDeliveryTime == "Standard",
                        onClick = { selectedDeliveryTime = "Standard" },
                        modifier = Modifier.weight(1f)
                    )
                    DeliveryTimeChip(
                        label = "Schedule",
                        subtitle = "Choose a time",
                        isSelected = selectedDeliveryTime == "Schedule",
                        onClick = { selectedDeliveryTime = "Schedule" },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
            }

            // Order Summary
            item {
                Text(
                    "Order Summary",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
                )
            }

            // Merchant + items
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController("merchant|$merchantName") }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        if (merchantName.isNotEmpty()) "$merchantName • $itemCount items"
                        else "$itemCount items",
                        fontSize = 16.sp
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
            }

            // Add promo code
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Add promo code", fontSize = 16.sp, color = Color.Gray)
                }
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
            }

            // Price breakdown
            item {
                Spacer(modifier = Modifier.height(8.dp))
                PriceRow("Subtotal", subtotal)
                PriceRow(
                    "Delivery Fee",
                    deliveryFee,
                    strikethrough = deliveryFee == 0.0
                )
                PriceRow("Taxes & Other Fees", taxesAndFees)
                HorizontalDivider(
                    color = Color(0xFFE0E0E0),
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "$${String.format("%.2f", total)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Account type
            item {
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Personal", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("account", fontSize = 14.sp, color = Color.Gray)
                }
            }

            // Privacy notice
            item {
                Text(
                    "By placing an order you agree to the terms of service. " +
                            "Your personal data will be processed in accordance with the privacy notice.",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun PriceRow(label: String, amount: Double, strikethrough: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp)
        if (strikethrough && amount == 0.0) {
            Text("$0.00", fontSize = 16.sp, color = Color(0xFF4CAF50))
        } else {
            Text("$${String.format("%.2f", amount)}", fontSize = 16.sp)
        }
    }
}

@Composable
private fun DeliveryTimeChip(
    label: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (isSelected) Modifier.border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                else Modifier.border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
            )
            .background(if (isSelected) Color.White else Color(0xFFF5F5F5))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column {
            Text(
                label,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}
