package com.example.ubereats_sim

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UnderDevelopmentScreen(title: String) {
    val onBack = LocalNavBack.current
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🚧", fontSize = 48.sp)
                Spacer(Modifier.height(16.dp))
                Text("Under Development", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(
                    "This feature is under development. Stay tuned.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, cartBadgeCount: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(containerColor = Color.White, contentColor = Color.Black) {
        BottomNavItem(Icons.Default.Home, "Home", selectedTab == 0, onClick = { onTabSelected(0) })
        BottomNavItem(
            Icons.Default.LocationOn,
            "Location",
            selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        BottomNavItem(Icons.Default.Search, "Search", selectedTab == 2, onClick = { onTabSelected(2) })
        BottomNavItem(
            Icons.Default.ShoppingCart,
            "Cart",
            selectedTab == 3,
            onClick = { onTabSelected(3) },
            badge = if (cartBadgeCount > 0) cartBadgeCount.toString() else null
        )
        BottomNavItem(Icons.Default.Person, "Account", selectedTab == 4, onClick = { onTabSelected(4) })
    }
}

@Composable
fun RowScope.BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    badge: String? = null
) {
    NavigationBarItem(
        icon = {
            BadgedBox(badge = { if (badge != null) Badge { Text(badge) } }) {
                Icon(icon, contentDescription = label)
            }
        },
        label = { Text(label) },
        selected = selected,
        onClick = onClick
    )
}
