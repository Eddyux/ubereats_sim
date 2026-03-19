package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.presenter.ProfilePresenter

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val presenter = ProfilePresenter(context)
    val userProfile = presenter.getUserProfile()
    val menuItems = presenter.getMenuItems()

    LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White)) {
        item { ProfileHeader(userProfile.name, userProfile.isVerified) }
        item { QuickActions() }
        items(menuItems) { item ->
            MenuItemRow(item.icon, item.title, item.subtitle)
        }
        item { VersionFooter() }
    }
}

@Composable
private fun ProfileHeader(name: String, isVerified: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(if (isVerified) "已验证" else "未验证", fontSize = 14.sp, color = Color.Gray)
        }
        Box(
            modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, null, Modifier.size(40.dp), tint = Color.Gray)
        }
    }
}

@Composable
private fun QuickActions() {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickActionBtn("❤️", "我的收藏", Modifier.weight(1f)) { nav("我的收藏") }
        QuickActionBtn("💼", "钱包", Modifier.weight(1f)) { nav("钱包") }
        QuickActionBtn("📄", "订单", Modifier.weight(1f)) { nav("订单") }
    }
}

@Composable
private fun QuickActionBtn(
    icon: String, label: String, modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp), color = Color(0xFFF5F5F5)
    ) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(icon, fontSize = 24.sp)
            Spacer(Modifier.height(8.dp))
            Text(label, fontSize = 12.sp)
        }
    }
}

@Composable
private fun MenuItemRow(icon: String, title: String, subtitle: String?) {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable { nav(title) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 24.sp, modifier = Modifier.width(40.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp)
            if (subtitle != null) Text(subtitle, fontSize = 14.sp, color = Color.Gray)
        }
        Icon(Icons.Default.KeyboardArrowRight, null, tint = Color.Gray)
    }
    HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
}

@Composable
private fun VersionFooter() {
    Text("v6.311.10000", fontSize = 12.sp, color = Color.Gray,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 24.dp))
}
