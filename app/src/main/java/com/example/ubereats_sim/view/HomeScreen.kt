package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.presenter.HomePresenter

private val UberGreen = Color(0xFF05944F)

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val presenter = HomePresenter(context)
    val recommended = presenter.getRecommendedRestaurants()
    val affordable = presenter.getAffordableRestaurants()
    val fastDelivery = presenter.getFastDeliveryRestaurants()
    val popularNearby = presenter.getPopularNearbyRestaurants()
    val nearbyStores = presenter.getNearbyStores()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("全部", "行程", "生鲜杂货", "便利店")

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        HomeGreenHeader()
        HomeTabRow(tabs, selectedTab) { selectedTab = it }

        if (selectedTab == 0) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { CategoryRow() }
                item { FilterRow() }
                item { SectionTitle("Uber Eats 优食推荐商家") }
                item { RestaurantGrid(recommended) }
                item { SectionTitle("经济实惠的美食") }
                item { RestaurantGrid(affordable) }
                item { SectionTitle("查看附近店铺") }
                item { NearbyStoreGrid(nearbyStores) }
                item { SectionTitle("快速派送") }
                item { RestaurantGrid(fastDelivery) }
                item { SectionTitle("所在街区的热门商家") }
                item { RestaurantGrid(popularNearby) }
                item { PromoBanner() }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        } else {
            PlaceholderTab(tabs[selectedTab])
        }
    }
}

@Composable
private fun HomeGreenHeader() {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(UberGreen)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { nav("定位") }
        ) {
            Text("纽约", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Icon(Icons.Default.ArrowDropDown, null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Icon(
            Icons.Default.Notifications, "通知", tint = Color.White,
            modifier = Modifier.size(24.dp).clickable { nav("通知") }
        )
    }
}

@Composable
private fun HomeTabRow(tabs: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    ScrollableTabRow(
        selectedTabIndex = selected,
        containerColor = Color.White,
        contentColor = Color.Black,
        edgePadding = 0.dp,
        divider = { HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0)) }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selected == index,
                onClick = { onSelect(index) },
                text = {
                    Text(title, fontWeight = if (selected == index) FontWeight.Bold else FontWeight.Normal)
                }
            )
        }
    }
}

@Composable
private fun PlaceholderTab(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("$name（开发中）", fontSize = 16.sp, color = Color.Gray)
    }
}
