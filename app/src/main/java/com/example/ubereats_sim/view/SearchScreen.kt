package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
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
import com.example.ubereats_sim.presenter.SearchPresenter

@Composable
fun SearchScreen() {
    val presenter = SearchPresenter()
    val recent = presenter.getRecentSearches()
    val snacks = presenter.getSnackShortcuts()
    val categories = presenter.getTopCategories()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item { SearchTopBar() }
        item { SectionHeader("Recent searches") }
        items(recent) { keyword ->
            SearchHistoryRow(keyword)
        }
        item { SectionHeader("Stock up on snacks") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(snacks) { item ->
                    SnackShortcutCard(item.emoji, item.label)
                }
            }
        }
        item { Spacer(Modifier.height(10.dp)) }
        item { SectionHeader("Top categories") }
        items(categories) { category ->
            TopCategoryRow(category.label)
        }
    }
}

@Composable
private fun SearchTopBar() {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable { nav("Back") }
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .clickable { nav("Search Uber Eats") },
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFF2F2F2)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                Spacer(Modifier.size(8.dp))
                Text("Search Uber Eats", color = Color.Gray)
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    )
}

@Composable
private fun SearchHistoryRow(keyword: String) {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { nav(keyword) }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
            Spacer(Modifier.size(8.dp))
            Text(keyword, fontSize = 15.sp)
        }
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
}

@Composable
private fun SnackShortcutCard(emoji: String, label: String) {
    val nav = LocalNavController.current
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier
            .height(90.dp)
            .clickable { nav(label) }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 22.sp)
            Spacer(Modifier.height(6.dp))
            Text(label, fontSize = 12.sp)
        }
    }
}

@Composable
private fun TopCategoryRow(label: String) {
    val nav = LocalNavController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { nav(label) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp)
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
}
