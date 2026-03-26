package com.example.ubereats_sim.view

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import com.example.ubereats_sim.LocalNavController

@Composable
fun SettingsHomeScreen() {
    val goBack = LocalNavBack.current
    val nav = LocalNavController.current
    val context = LocalContext.current

    val address = "119-1, Section 3, Mingzhi Rd, Zhicheng Dist, New Taipei City, 221"

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
            Text("Home", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        // Map placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(0xFFE8E8E8)),
            contentAlignment = Alignment.Center
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
            // Pin icon overlay
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Address section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
            Spacer(Modifier.width(12.dp))
            Text(
                address,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { nav("SettingsHomeSet") }) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.Black
                )
            }
        }

        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)

        Spacer(Modifier.weight(1f))

        // Delete Home
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { goBack() }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text("Delete Home", fontSize = 16.sp, color = Color.Red)
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun SettingsHomeSetScreen() {
    val goBack = LocalNavBack.current
    var addressText by remember { mutableStateOf("119-1, Section 3, Mingzhi Rd") }
    var aptText by remember { mutableStateOf("") }
    var entryCode by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top bar with search
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { goBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF2F2F2)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = addressText,
                        onValueChange = { addressText = it },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        cursorBrush = SolidColor(Color.Black),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (addressText.isEmpty()) {
                                Text("Search address", fontSize = 16.sp, color = Color.Gray)
                            }
                            innerTextField()
                        }
                    )
                    if (addressText.isNotEmpty()) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Clear",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { addressText = "" },
                            tint = Color.Gray
                        )
                    }
                }
            }
            Spacer(Modifier.width(8.dp))
        }

        // Map
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFFE8E8E8)),
            contentAlignment = Alignment.Center
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
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Address details
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Address", fontSize = 14.sp, color = Color.Gray)
            Spacer(Modifier.height(4.dp))
            Text(
                "119-1, Section 3, Mingzhi Rd, Zhicheng Dist, New Taipei City, 221",
                fontSize = 16.sp
            )
            Spacer(Modifier.height(20.dp))

            // Apartment field
            Text("Apartment, suite, or floor", fontSize = 14.sp, color = Color.Gray)
            Spacer(Modifier.height(4.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF2F2F2),
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = aptText,
                    onValueChange = { aptText = it },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    cursorBrush = SolidColor(Color.Black),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (aptText.isEmpty()) {
                            Text("e.g. Apt 4B", fontSize = 16.sp, color = Color.Gray)
                        }
                        innerTextField()
                    }
                )
            }
            Spacer(Modifier.height(16.dp))

            // Entry code
            Text("Entry code", fontSize = 14.sp, color = Color.Gray)
            Spacer(Modifier.height(4.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF2F2F2),
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = entryCode,
                    onValueChange = { entryCode = it },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    cursorBrush = SolidColor(Color.Black),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (entryCode.isEmpty()) {
                            Text("e.g. #1234", fontSize = 16.sp, color = Color.Gray)
                        }
                        innerTextField()
                    }
                )
            }
        }

        Spacer(Modifier.weight(1f))

        // Save button
        Button(
            onClick = { goBack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Save Home", fontSize = 16.sp, color = Color.White)
        }
    }
}
