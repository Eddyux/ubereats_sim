package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalNavController

private data class BuildingOption(
    val emoji: String,
    val title: String,
    val description: String
)

@Composable
fun SettingsHomeScreen() {
    val goBack = LocalNavBack.current
    val nav = LocalNavController.current

    val buildingOptions = listOf(
        BuildingOption("🏠", "House", "A standalone residential building"),
        BuildingOption("🏢", "Apartment", "A unit within a larger building"),
        BuildingOption("🏛️", "Office", "A commercial office building"),
        BuildingOption("🏨", "Hotel", "A hotel or hospitality building"),
        BuildingOption("📍", "Other", "Another type of building")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top bar: Back + Skip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { goBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(Modifier.weight(1f))
            TextButton(onClick = { goBack() }) {
                Text("Skip", fontSize = 16.sp, color = Color.Black)
            }
        }

        // Illustration area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color(0xFFF5F0E8)),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text("🏠", fontSize = 48.sp)
                Spacer(Modifier.width(8.dp))
                Text("🏢", fontSize = 64.sp)
                Spacer(Modifier.width(8.dp))
                Text("🏨", fontSize = 48.sp)
            }
        }

        Spacer(Modifier.height(24.dp))

        // Title and description
        Text(
            "Choose your building",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Telling us about your building type helps improve delivery accuracy and makes it easier for couriers to find you.",
            fontSize = 15.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(20.dp))

        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)

        // Building options
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(buildingOptions) { option ->
                BuildingOptionRow(option) {
                    nav("SettingsHomeSet")
                }
            }
        }
    }
}

@Composable
private fun BuildingOptionRow(option: BuildingOption, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF2F2F2),
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(option.emoji, fontSize = 24.sp)
            }
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(option.title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(option.description, fontSize = 14.sp, color = Color.Gray)
        }
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
    HorizontalDivider(
        color = Color(0xFFF0F0F0),
        thickness = 0.5.dp,
        modifier = Modifier.padding(start = 76.dp)
    )
}
