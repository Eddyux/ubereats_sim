package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavController

@Composable
fun RidesTabContent() {
    val nav = LocalNavController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text("Request a ride", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DropDownChip("Pickup now") { nav("Pickup now") }
            DropDownChip("For me") { nav("For me") }
        }
        Spacer(Modifier.height(20.dp))
        LocationInputRow("●", "Pickup location") { nav("Pickup location") }
        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.8.dp)
        LocationInputRow("■", "Dropoff location") { nav("Dropoff location") }
    }
}

@Composable
private fun DropDownChip(text: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF2F2F2),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 14.sp)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
    }
}

@Composable
private fun LocationInputRow(marker: String, placeholder: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(marker, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(end = 10.dp))
        Text(placeholder, fontSize = 16.sp, color = Color(0xFF444444))
    }
}
