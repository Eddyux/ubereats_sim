package com.example.ubereats_sim.presenter

import android.content.Context
import com.example.ubereats_sim.model.DataLoader
import com.example.ubereats_sim.model.MenuItem
import com.example.ubereats_sim.model.UserProfile

class ProfilePresenter(private val context: Context) {

    fun getUserProfile(): UserProfile {
        return DataLoader.loadUserProfile(context)
    }

    fun getMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("👨‍👩‍👧‍👦", "Family & Teens", "Teen and adult accounts"),
            MenuItem("📋", "Lists"),
            MenuItem("🚗", "Rides"),
            MenuItem("🏷️", "Offers"),
            MenuItem("🎁", "Send a gift"),
            MenuItem("❓", "Help"),
            MenuItem("👥", "Saved groups"),
            MenuItem("💼", "Set up your business account", "Automatically pay for travel and meals"),
            MenuItem("🤝", "Partner rewards"),
            MenuItem("⭐", "Uber One", "Renews on March 25"),
            MenuItem("🔒", "Privacy"),
            MenuItem("♿", "Accessibility"),
            MenuItem("📢", "Communication"),
            MenuItem("🚙", "Drive or deliver and earn"),
            MenuItem("🎙️", "Voice shortcuts"),
            MenuItem("👤", "Manage Uber account"),
            MenuItem("⚠️", "Allergy settings"),
            MenuItem("ℹ️", "About"),
            MenuItem("⚙️", "Settings")
        )
    }
}
