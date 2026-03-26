package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalNavController

data class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String? = null,
    val route: String? = null
)

@Composable
fun SettingsScreen() {
    val goBack = LocalNavBack.current
    val nav = LocalNavController.current

    val settingsItems = listOf(
        SettingsItem(Icons.Default.Home, "Home", "119-1, Section 3, Mingzhi Rd", "SettingsHome"),
        SettingsItem(Icons.Default.Build, "Work", "Add work"),
        SettingsItem(Icons.Default.Star, "Shortcuts"),
        SettingsItem(Icons.Default.Warning, "Safety"),
        SettingsItem(Icons.Default.Lock, "Privacy", route = "Privacy"),
        SettingsItem(Icons.Default.Settings, "Appearance"),
        SettingsItem(Icons.Default.Notifications, "Communication", "Email, Push notifications, SMS"),
        SettingsItem(Icons.Default.Settings, "Accessibility", route = "Accessibility")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SettingsTopBar(onBack = { goBack() })
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(settingsItems) { item ->
                SettingsItemRow(item) {
                    nav(item.route ?: item.title)
                }
            }
            item { HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 6.dp) }
            item { SignOutRow() }
        }
    }
}

@Composable
private fun SettingsTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text("Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SettingsItemRow(item: SettingsItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            item.icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontSize = 16.sp)
            if (item.subtitle != null) {
                Text(item.subtitle, fontSize = 14.sp, color = Color.Gray)
            }
        }
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
    HorizontalDivider(
        color = Color(0xFFF0F0F0),
        thickness = 0.5.dp,
        modifier = Modifier.padding(start = 56.dp)
    )
}

@Composable
private fun SignOutRow() {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { nav("Sign out") }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ExitToApp,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
        Spacer(Modifier.width(16.dp))
        Text("Sign out", fontSize = 16.sp)
    }
    Spacer(Modifier.height(32.dp))
}
