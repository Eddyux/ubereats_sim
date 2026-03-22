package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@Composable
fun HearingScreen() {
    val navBack = LocalNavBack.current
    var selectedOption by remember { mutableStateOf("not_deaf") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(horizontal = 16.dp)
                .background(Color(0xFFF0F7FF), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("👂", fontSize = 64.sp)
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "Hearing",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Disclose your hearing status to help drivers and couriers communicate with you better.",
            fontSize = 15.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(24.dp))

        HearingOption(
            label = "I'm deaf",
            isSelected = selectedOption == "deaf",
            onClick = { selectedOption = "deaf" }
        )

        HearingOption(
            label = "I'm hard of hearing",
            isSelected = selectedOption == "hard_of_hearing",
            onClick = { selectedOption = "hard_of_hearing" }
        )

        HearingOption(
            label = "I'm not deaf or hard of hearing",
            isSelected = selectedOption == "not_deaf",
            onClick = { selectedOption = "not_deaf" }
        )
    }
}

@Composable
private fun HearingOption(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .border(2.dp, if (isSelected) Color.Black else Color.Gray, CircleShape)
                .then(
                    if (isSelected) Modifier.background(Color.Black, CircleShape)
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(Color.White, CircleShape)
                )
            }
        }
        Spacer(Modifier.width(16.dp))
        Text(label, fontSize = 16.sp)
    }
}
