package com.example.ubereats_sim.view

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val groceryMerchants = presenter.getGroceryMerchants()
    val convenienceMerchants = presenter.getConvenienceMerchants()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("All", "Rides", "Grocery", "Convenience")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (selectedTab == 1) {
            RidesTopBar(tabs, selectedTab) { selectedTab = it }
        } else {
            HomeGreenHeader()
            HomeTabRow(tabs, selectedTab) { selectedTab = it }
        }

        when (selectedTab) {
            0 -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item { CategoryRow() }
                    item { FilterRow() }
                    item { SectionTitle("Uber Eats Recommended") }
                    item { RestaurantGrid(recommended) }
                    item { SectionTitle("Great Value Eats") }
                    item { RestaurantGrid(affordable) }
                    item { SectionTitle("Browse nearby stores") }
                    item { NearbyStoreGrid(nearbyStores) }
                    item { SectionTitle("Fast delivery") }
                    item { RestaurantGrid(fastDelivery) }
                    item { SectionTitle("Popular in your area") }
                    item { RestaurantGrid(popularNearby) }
                    item { PromoBanner() }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }

            1 -> RidesTabContent()
            2 -> GroceryTabContent(
                nearbyStores = nearbyStores,
                featuredRestaurants = groceryMerchants,
                featuredDeals = groceryMerchants.asReversed()
            )
            3 -> ConvenienceTabContent(convenienceMerchants)
            else -> PlaceholderTab(tabs[selectedTab])
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
            modifier = Modifier.clickable { nav("Location") }
        ) {
            Text("New York", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Icon(
            Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable { nav("Promotions") }
        )
    }
}

@Composable
private fun RidesTopBar(tabs: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScrollableTabRow(
            modifier = Modifier.weight(1f),
            selectedTabIndex = selected,
            containerColor = Color.White,
            contentColor = Color.Black,
            edgePadding = 0.dp,
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selected == index,
                    onClick = { onSelect(index) },
                    text = {
                        Text(
                            title,
                            fontWeight = if (selected == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
        Icon(
            Icons.Default.Notifications,
            contentDescription = "Promotions",
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(24.dp)
                .clickable { nav("Promotions") }
        )
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
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
                    Text(
                        title,
                        fontWeight = if (selected == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}

@Composable
private fun PlaceholderTab(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("$name (Under development)", fontSize = 16.sp, color = Color.Gray)
    }
}
