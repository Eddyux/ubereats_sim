package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalRideDropoff

private data class RideOption(
    val name: String,
    val icon: String,
    val price: String,
    val waitTime: String,
    val tripTime: String = "",
    val tag: String = "",
    val description: String = ""
)

private data class RideCategory(
    val title: String,
    val options: List<RideOption>
)

@Composable
fun ChooseRideScreen() {
    val navBack = LocalNavBack.current
    val (dropoffLocation, _) = LocalRideDropoff.current
    var selectedRide by remember { mutableStateOf("UberX") }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val categories = remember {
        listOf(
            RideCategory(
                "Popular", listOf(
                    RideOption("UberX", "🚗", "$83.95", "1 min away", "0:52", "Faster", "Affordable rides"),
                    RideOption("Taxi", "🚕", "$67-89", "2 min away", "", "", "Ride a taxi")
                )
            ),
            RideCategory(
                "Economy", listOf(
                    RideOption("UberXL", "🚙", "$116.15", "8 min away", "0:52", "", "Extra seats"),
                    RideOption("Share", "👥", "$52.40", "5 min away", "1:05", "", "Share your ride")
                )
            ),
            RideCategory(
                "Premium", listOf(
                    RideOption("Black", "🖤", "$156.00", "3 min away", "0:50", "", "Premium sedan"),
                    RideOption("Black SUV", "⬛", "$198.00", "5 min away", "0:50", "", "Premium SUV")
                )
            ),
            RideCategory(
                "More", listOf(
                    RideOption("Comfort", "💺", "$98.50", "4 min away", "0:52", "", "Newer cars, extra legroom"),
                    RideOption("Green", "🌿", "$85.00", "6 min away", "0:52", "", "Electric or hybrid")
                )
            )
        )
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = {
                Text(
                    "Ride Requested",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    "Your $selectedRide is on the way!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmDialog = false
                        navBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("OK", color = Color.White)
                }
            }
        )
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            Column(modifier = Modifier.background(Color.White)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Personal", fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                }
                Button(
                    onClick = { showConfirmDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        "Request $selectedRide",
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
            // Back button
            item {
                IconButton(
                    onClick = { navBack() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }

            // Map area
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color(0xFFD4E8D4))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            dropoffLocation.ifEmpty { "Destination" },
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("1 min away", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            // Pickup now / For me chips
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RideChip("Pickup now")
                    RideChip("For me")
                }
            }

            // Ride categories
            categories.forEach { category ->
                item {
                    Text(
                        category.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp)
                    )
                }
                items(category.options) { option ->
                    RideOptionRow(
                        option = option,
                        isSelected = selectedRide == option.name,
                        onClick = { selectedRide = option.name }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun RideChip(text: String) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF2F2F2)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 14.sp)
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun RideOptionRow(option: RideOption, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isSelected) Modifier.border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                else Modifier
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Car icon
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(option.icon, fontSize = 28.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Info
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    option.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                if (option.tag.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFE8F5E9)
                    ) {
                        Text(
                            option.tag,
                            fontSize = 11.sp,
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Text(
                buildString {
                    append(option.waitTime)
                    if (option.tripTime.isNotEmpty()) append(" • ${option.tripTime}")
                    if (option.description.isNotEmpty()) append(" • ${option.description}")
                },
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        // Price
        Text(
            option.price,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
