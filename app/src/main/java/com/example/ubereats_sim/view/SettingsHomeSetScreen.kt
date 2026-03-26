package com.example.ubereats_sim.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack

@Composable
fun SettingsHomeSetScreen() {
    val goBack = LocalNavBack.current
    val context = LocalContext.current
    var buildingType by remember { mutableStateOf("House") }
    var aptFloor by remember { mutableStateOf("") }
    var buildingName by remember { mutableStateOf("") }
    var dropoffOption by remember { mutableStateOf("Meet at my door") }
    var deliveryInstructions by remember { mutableStateOf("") }
    var selectedLabel by remember { mutableIntStateOf(0) } // 0=Home, 1=Work, 2=Other

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { goBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("Address details", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Map with Edit pin
            AddressMapSection(context)

            // Address info
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(Modifier.height(12.dp))
                Text(
                    "119-1, Section 3, Mingzhi Rd, Zhicheng Dist, New Taipei City, 221",
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(20.dp))
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                Spacer(Modifier.height(16.dp))

                // Building type
                AddressDetailField("Building type", buildingType) { buildingType = it }
                Spacer(Modifier.height(16.dp))

                // Apt/Suite/Floor
                AddressDetailField("Apt/Suite/Floor", aptFloor, "e.g. Apt 4B") { aptFloor = it }
                Spacer(Modifier.height(16.dp))

                // Business/Building name
                AddressDetailField("Business/Building name", buildingName, "e.g. Main Tower") { buildingName = it }
                Spacer(Modifier.height(20.dp))

                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                Spacer(Modifier.height(16.dp))

                // Dropoff options
                Text("Dropoff options", fontSize = 14.sp, color = Color.Gray)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(dropoffOption, fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
                Spacer(Modifier.height(16.dp))

                // Delivery instructions
                Text("Delivery instructions", fontSize = 14.sp, color = Color.Gray)
                Spacer(Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF2F2F2),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicTextField(
                        value = deliveryInstructions,
                        onValueChange = { deliveryInstructions = it },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        cursorBrush = SolidColor(Color.Black),
                        decorationBox = { innerTextField ->
                            if (deliveryInstructions.isEmpty()) {
                                Text("Add instructions for the courier", fontSize = 16.sp, color = Color.Gray)
                            }
                            innerTextField()
                        }
                    )
                }
                Spacer(Modifier.height(12.dp))

                // Add photos
                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Add photos", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.height(16.dp))

                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                Spacer(Modifier.height(16.dp))

                // Address label
                Text("Address label", fontSize = 14.sp, color = Color.Gray)
                Spacer(Modifier.height(8.dp))
                AddressLabelSelector(selectedLabel) { selectedLabel = it }
                Spacer(Modifier.height(16.dp))
            }
        }

        // Save button
        Button(
            onClick = { goBack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Save and continue", fontSize = 16.sp, color = Color.White)
        }
    }
}

@Composable
private fun AddressMapSection(context: android.content.Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color(0xFFE8E8E8))
    ) {
        val bitmap = remember {
            try {
                val stream = context.assets.open("location_map.png")
                android.graphics.BitmapFactory.decodeStream(stream)
            } catch (e: Exception) { null }
        }
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Map",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Icon(
            Icons.Default.LocationOn,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(40.dp).align(Alignment.Center)
        )
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
                .clickable { }
        ) {
            Text(
                "Edit pin",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun AddressDetailField(
    label: String,
    value: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit
) {
    Text(label, fontSize = 14.sp, color = Color.Gray)
    Spacer(Modifier.height(4.dp))
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF2F2F2),
        modifier = Modifier.fillMaxWidth()
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            cursorBrush = SolidColor(Color.Black),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (value.isEmpty() && placeholder.isNotEmpty()) {
                    Text(placeholder, fontSize = 16.sp, color = Color.Gray)
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun AddressLabelSelector(selected: Int, onSelect: (Int) -> Unit) {
    val labels = listOf("Home", "Work", "Other")
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        labels.forEachIndexed { index, label ->
            val isSelected = index == selected
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) Color.Black else Color.White,
                modifier = Modifier
                    .border(1.dp, Color.Black, RoundedCornerShape(20.dp))
                    .clickable { onSelect(index) }
            ) {
                Text(
                    label,
                    fontSize = 14.sp,
                    color = if (isSelected) Color.White else Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}
