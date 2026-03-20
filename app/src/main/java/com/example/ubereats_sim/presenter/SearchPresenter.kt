package com.example.ubereats_sim.presenter

import com.example.ubereats_sim.model.SearchCategory
import com.example.ubereats_sim.model.SnackShortcut

class SearchPresenter {

    fun getRecentSearches(): List<String> {
        return listOf("Pizza", "Sushi", "Kosher", "Burger King")
    }

    fun getSnackShortcuts(): List<SnackShortcut> {
        return listOf(
            SnackShortcut("🥔", "Chips"),
            SnackShortcut("🍿", "Popcorn"),
            SnackShortcut("🍫", "Chocolate"),
            SnackShortcut("🥤", "Soda")
        )
    }

    fun getTopCategories(): List<SearchCategory> {
        return listOf(
            SearchCategory("Kosher"),
            SearchCategory("Pizza"),
            SearchCategory("Sushi"),
            SearchCategory("Burgers"),
            SearchCategory("Sandwiches"),
            SearchCategory("Coffee")
        )
    }
}
