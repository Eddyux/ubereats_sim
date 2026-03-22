package com.example.ubereats_sim.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.presenter.LocationPresenter

@Composable
fun LocationScreen() {
    val nav = LocalNavController.current
    val presenter = LocationPresenter()
    val filters = presenter.getFilters()
    val markers = presenter.getMapMarkers()
    val pickupSpots = presenter.getPickupSpots()
    val context = LocalContext.current
    val mapImage = remember {
        runCatching {
            context.assets.open("location_map.png").use { input ->
                val bytes = input.readBytes()
                android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    ?.asImageBitmap()
            }
        }.getOrNull()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchPickupBar()
        FilterRow(filters)

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFE9EEF2))
        ) {
            if (mapImage != null) {
                Image(
                    bitmap = mapImage,
                    contentDescription = "Map",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            markers.forEach { marker ->
                Column(
                    modifier = Modifier
                        .offset(x = maxWidth * marker.xPercent, y = maxHeight * marker.yPercent)
                        .clickable { nav(marker.storeName) }
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        shadowElevation = 2.dp
                    ) {
                        Text(
                            marker.deal,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(marker.storeName, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp, start = 2.dp))
                }
            }
        }

        Text(
            "Pickup spots near you",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(pickupSpots) { spot ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { nav(spot.name) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(spot.name, fontWeight = FontWeight.Bold)
                            Text("${spot.eta} · ⭐ ${spot.rating}", fontSize = 12.sp, color = Color.Gray)
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFF05944F).copy(alpha = 0.1f), RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFF05944F),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchPickupBar() {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .clickable { nav("Search pickup spots nearby") },
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFF2F2F2)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                Spacer(Modifier.width(8.dp))
                Text("Search pickup spots nearby", color = Color.Gray)
            }
        }
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(Color(0xFF05944F), RoundedCornerShape(22.dp))
                .clickable { nav("Locate me") },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun FilterRow(filters: List<String>) {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEachIndexed { index, filter ->
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = if (index == 0) Color.Black else Color(0xFFF2F2F2),
                modifier = Modifier.clickable { nav(filter) }
            ) {
                Text(
                    filter,
                    color = if (index == 0) Color.White else Color.Black,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                    fontSize = 13.sp
                )
            }
        }
    }
}
