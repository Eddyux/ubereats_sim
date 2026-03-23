package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalNavController

@Composable
fun PaymentScreen() {
    val navBack = LocalNavBack.current
    val navController = LocalNavController.current
    var selectedTab by remember { mutableStateOf("Personal") }
    var uberBalancesEnabled by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navBack() }
                )
            }
        }

        item {
            Text(
                "Pay with",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabButton(
                    text = "👤 Personal",
                    isSelected = selectedTab == "Personal",
                    onClick = { selectedTab = "Personal" },
                    modifier = Modifier.weight(1f)
                )
                TabButton(
                    text = "💼 Business",
                    isSelected = selectedTab == "Business",
                    onClick = { selectedTab = "Business" },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            Text(
                "Uber balances",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Uber balances", fontSize = 16.sp)
                Switch(
                    checked = uberBalancesEnabled,
                    onCheckedChange = { uberBalancesEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color.Black
                    )
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Uber",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black, RoundedCornerShape(8.dp))
                        .wrapContentSize(Alignment.Center),
                    color = Color.White
                )
                Spacer(Modifier.width(12.dp))
                Text("Uber Cash: $0.00", fontSize = 16.sp)
            }
        }

        item { Spacer(Modifier.height(24.dp)) }

        item {
            Text(
                "Payment methods",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController("Checkout") }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "💳",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF4CAF50), RoundedCornerShape(8.dp))
                        .wrapContentSize(Alignment.Center)
                )
                Spacer(Modifier.width(12.dp))
                Text("yx666", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(12.dp))
                Text("Add payment method", fontSize = 16.sp)
            }
        }

        item { HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp) }

        item { Spacer(Modifier.height(24.dp)) }

        item {
            Text(
                "Vouchers",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(12.dp))
                Text("Add voucher code", fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .background(
                if (isSelected) Color.White else Color(0xFFF5F5F5),
                RoundedCornerShape(24.dp)
            )
            .then(
                if (isSelected) Modifier.border(2.dp, Color.Black, RoundedCornerShape(24.dp))
                else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
