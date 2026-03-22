package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalNavController

@Composable
fun PrivacyScreen() {
    val navBack = LocalNavBack.current
    val nav = LocalNavController.current

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
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navBack() }
                )
            }
        }

        item {
            Text(
                "Privacy Center",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        item {
            Text(
                "Take control of your privacy and learn how we protect it.",
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            Text(
                "Your data and privacy at Uber",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PrivacyCard(
                    icon = "📊",
                    title = "View my usage summary",
                    modifier = Modifier.weight(1f)
                )
                PrivacyCard(
                    icon = "📋",
                    title = "Request my personal data",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item { Spacer(Modifier.height(24.dp)) }
        item { HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp) }

        item {
            Text(
                "Ads and Data",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        item {
            PrivacyMenuItem(
                title = "Personalized Offers, Promos, and Marketing Content",
                onClick = { nav("Personalized Offers") }
            )
        }

        item { HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp) }

        item {
            Text(
                "Location sharing",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        item {
            PrivacyMenuItem(
                title = "Live location",
                onClick = { nav("Live location") }
            )
        }

        item { HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp) }

        item {
            Text(
                "Account security",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        item {
            PrivacyMenuItem(
                title = "Account Deletion",
                onClick = { nav("Account Deletion") }
            )
        }

        item { HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp) }
        item { Spacer(Modifier.height(24.dp)) }

        item {
            Text(
                "How do we approach privacy at Uber?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        item {
            PrivacyMenuItem(
                title = "View our Privacy Overview Page",
                onClick = { nav("Privacy Overview") }
            )
        }

        item {
            PrivacyMenuItem(
                title = "Submit a privacy inquiry",
                onClick = { nav("Privacy Inquiry") }
            )
        }

        item { Spacer(Modifier.height(32.dp)) }
    }
}

@Composable
private fun PrivacyCard(icon: String, title: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.clickable { },
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(icon, fontSize = 28.sp)
            Spacer(Modifier.height(12.dp))
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun PrivacyMenuItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
}
