package com.example.ubereats_sim.model

data class UserProfile(
    val name: String,
    val isVerified: Boolean,
    val avatarUrl: String? = null
)

data class MenuItem(
    val icon: String,
    val title: String,
    val subtitle: String? = null
)
