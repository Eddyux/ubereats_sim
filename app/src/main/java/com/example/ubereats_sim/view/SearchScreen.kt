package com.example.ubereats_sim.view

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavController
import com.example.ubereats_sim.presenter.SearchPresenter

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    val nav = LocalNavController.current
    val selectTab = com.example.ubereats_sim.LocalTabSelector.current
    val presenter = remember(context) { SearchPresenter(context) }
    val recentSearches = remember {
        mutableStateListOf<String>().apply { addAll(presenter.getRecentSearches()) }
    }
    val snacks = remember(presenter) { presenter.getSnackShortcuts() }
    val categories = remember(presenter) { presenter.getTopCategories() }
    var query by rememberSaveable { mutableStateOf("") }
    val results = remember(query, presenter) { presenter.search(query) }

    fun submitSearch(term: String) {
        val cleaned = term.trim()
        if (cleaned.isBlank()) return
        recentSearches.removeAll { it.equals(cleaned, ignoreCase = true) }
        recentSearches.add(0, cleaned)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            SearchTopBar(
                query = query,
                onValueChange = { query = it },
                onBack = { selectTab(0) },
                onClear = { query = "" },
                onSubmit = { submitSearch(query) }
            )
        }

        if (query.isBlank()) {
            item { SearchSectionHeader("Recent searches") }
            items(recentSearches) { keyword ->
                SearchHistoryRow(keyword) {
                    query = keyword
                    submitSearch(keyword)
                }
            }

            item { SearchSectionHeader("Stock up on snacks") }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(snacks) { item ->
                        SearchSnackShortcutCard(item.emoji, item.label) {
                            query = item.label
                            submitSearch(item.label)
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(10.dp)) }
            item { SearchSectionHeader("Top categories") }
            items(categories) { category ->
                SearchTopCategoryRow(category.label) {
                    query = category.label
                    submitSearch(category.label)
                }
            }
        } else {
            if (results.merchants.isEmpty() && results.dishes.isEmpty() && results.categories.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("No matches found", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(6.dp))
                            Text("Try another merchant, dish, or category.", color = Color.Gray)
                        }
                    }
                }
            } else {
                if (results.merchants.isNotEmpty()) {
                    item { SearchSectionHeader("Merchants") }
                    items(results.merchants) { merchant ->
                        SearchMerchantRow(merchant) {
                            submitSearch(query)
                            nav("merchant|${merchant.name}")
                        }
                    }
                }
                if (results.dishes.isNotEmpty()) {
                    item { SearchSectionHeader("Dishes") }
                    items(results.dishes) { dish ->
                        SearchDishRow(dish) {
                            submitSearch(query)
                            nav("merchant|${dish.merchantName}")
                        }
                    }
                }
                if (results.categories.isNotEmpty()) {
                    item { SearchSectionHeader("Categories") }
                    items(results.categories) { category ->
                        SearchTopCategoryRow(category.label) {
                            query = category.label
                            submitSearch(category.label)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchTopBar(
    query: String,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
    onSubmit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        TextField(
            value = query,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("Search Uber Eats") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
            },
            trailingIcon = {
                if (query.isNotBlank()) {
                    IconButton(onClick = onClear) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF2F2F2),
                unfocusedContainerColor = Color(0xFFF2F2F2),
                disabledContainerColor = Color(0xFFF2F2F2),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSubmit() })
        )
    }
}

