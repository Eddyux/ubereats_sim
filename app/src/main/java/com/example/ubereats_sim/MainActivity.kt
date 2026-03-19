package com.example.ubereats_sim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.ui.theme.Test05Theme
import com.example.ubereats_sim.view.CartScreen
import com.example.ubereats_sim.view.HomeScreen
import com.example.ubereats_sim.view.ProfileScreen

val LocalNavController = compositionLocalOf<(String) -> Unit> { {} }
val LocalNavBack = compositionLocalOf<() -> Unit> { {} }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test05Theme { MainScreen() }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var subPage by remember { mutableStateOf<String?>(null) }

    CompositionLocalProvider(
        LocalNavController provides { page -> subPage = page },
        LocalNavBack provides { subPage = null }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (subPage == null) {
                    BottomNavigationBar(selectedTab) { selectedTab = it }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                if (subPage != null) {
                    UnderDevelopmentScreen(subPage!!)
                } else {
                    when (selectedTab) {
                        0 -> HomeScreen()
                        1 -> UnderDevelopmentScreen("定位")
                        2 -> UnderDevelopmentScreen("搜索")
                        3 -> CartScreen()
                        4 -> ProfileScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun UnderDevelopmentScreen(title: String) {
    val onBack = LocalNavBack.current
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, "返回")
            }
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🚧", fontSize = 48.sp)
                Spacer(Modifier.height(16.dp))
                Text("开发中", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("该功能正在开发中，敬请期待", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(containerColor = Color.White, contentColor = Color.Black) {
        BottomNavItem(Icons.Default.Home, "首页", selectedTab == 0, onClick = { onTabSelected(0) })
        BottomNavItem(Icons.Default.LocationOn, "定位", selectedTab == 1, onClick = { onTabSelected(1) })
        BottomNavItem(Icons.Default.Search, "搜索", selectedTab == 2, onClick = { onTabSelected(2) })
        BottomNavItem(Icons.Default.ShoppingCart, "购物车", selectedTab == 3, onClick = { onTabSelected(3) }, badge = "1")
        BottomNavItem(Icons.Default.Person, "我的", selectedTab == 4, onClick = { onTabSelected(4) })
    }
}

@Composable
fun RowScope.BottomNavItem(
    icon: ImageVector, label: String, selected: Boolean,
    onClick: () -> Unit, badge: String? = null
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
