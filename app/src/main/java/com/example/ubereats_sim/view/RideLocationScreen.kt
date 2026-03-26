package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalRidePickup
import com.example.ubereats_sim.LocalRideDropoff

data class SavedPlace(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

data class SuggestedLocation(
    val title: String,
    val subtitle: String
)

@Composable
fun RideLocationScreen(isPickup: Boolean = true) {
    val goBack = LocalNavBack.current
    val (_, setPickup) = LocalRidePickup.current
    val (_, setDropoff) = LocalRideDropoff.current
    var searchText by remember { mutableStateOf("") }

    fun selectLocation(name: String) {
        if (isPickup) setPickup(name) else setDropoff(name)
        goBack()
    }

    val searchBarPlaceholder = if (isPickup) "Pickup location" else "Dropoff location"

    val savedPlaces = remember {
        listOf(
            SavedPlace(Icons.Default.Home, "Home", "119-1, Section 3, Mingzhi Rd"),
            SavedPlace(Icons.Default.Build, "Work", "Add work")
        )
    }

    val suggestedLocations = remember {
        listOf(
            SuggestedLocation("Times Square", "Manhattan, NY 10036"),
            SuggestedLocation("Penn Station", "234 W 31st St, New York, NY 10001"),
            SuggestedLocation("Grand Central Terminal", "89 E 42nd St, New York, NY 10017"),
            SuggestedLocation("Central Park", "New York, NY 10024"),
            SuggestedLocation("Brooklyn Bridge", "New York, NY 10038"),
            SuggestedLocation("JFK International Airport", "Queens, NY 11430")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        PickupSearchBar(searchText, onTextChange = { searchText = it }, onBack = { goBack() }, placeholder = searchBarPlaceholder)
        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { SavedPlacesSection(savedPlaces, onSelect = { selectLocation(it) }) }
            item {
                HorizontalDivider(
                    color = Color(0xFFE0E0E0),
                    thickness = 6.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            item { SuggestedSectionHeader() }
            items(suggestedLocations) { location ->
                SuggestedLocationRow(location, onSelect = { selectLocation(location.title) })
            }
        }
    }
}

@Composable
private fun PickupSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onBack: () -> Unit,
    placeholder: String = "Pickup location"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF2F2F2)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = onTextChange,
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    cursorBrush = SolidColor(Color.Black),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(placeholder, fontSize = 16.sp, color = Color.Gray)
                        }
                        innerTextField()
                    }
                )
                if (text.isNotEmpty()) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Clear",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onTextChange("") },
                        tint = Color.Gray
                    )
                }
            }
        }
        Spacer(Modifier.width(8.dp))
    }
}

@Composable
private fun SavedPlacesSection(places: List<SavedPlace>, onSelect: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text("Saved places", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        places.forEach { place ->
            SavedPlaceRow(place, onSelect = { onSelect(place.title) })
        }
    }
}

@Composable
private fun SavedPlaceRow(place: SavedPlace, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF2F2F2),
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                place.icon,
                contentDescription = null,
                modifier = Modifier.padding(8.dp),
                tint = Color.Black
            )
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(place.title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(place.subtitle, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun SuggestedSectionHeader() {
    Text(
        "Suggested",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun SuggestedLocationRow(location: SuggestedLocation, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(location.title, fontSize = 16.sp)
            Text(location.subtitle, fontSize = 14.sp, color = Color.Gray)
        }
    }
    HorizontalDivider(
        color = Color(0xFFF0F0F0),
        thickness = 0.5.dp,
        modifier = Modifier.padding(start = 52.dp)
    )
}
