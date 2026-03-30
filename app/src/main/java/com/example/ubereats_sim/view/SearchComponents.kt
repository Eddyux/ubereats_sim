package com.example.ubereats_sim.view

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.model.SearchDishResult
import com.example.ubereats_sim.model.SearchMerchantResult

@Composable
fun SearchSectionHeader(title: String) {
    Text(
        title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    )
}

@Composable
fun SearchHistoryRow(keyword: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
            Spacer(Modifier.size(8.dp))
            Text(keyword, fontSize = 15.sp)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
}

@Composable
fun SearchSnackShortcutCard(emoji: String, label: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier
            .height(90.dp)
            .clickable(onClick = onClick)
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
fun SearchTopCategoryRow(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp)
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
}

@Composable
fun SearchMerchantRow(merchant: SearchMerchantResult, onClick: () -> Unit) {
    val merchantImage = rememberMerchantImage(merchant.name, 180, 180)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            if (merchantImage != null) {
                Image(
                    bitmap = merchantImage,
                    contentDescription = merchant.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("🍽", fontSize = 24.sp)
            }
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(merchant.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(2.dp))
            Text(merchant.subtitle, fontSize = 13.sp, color = Color.Gray)
            Spacer(Modifier.height(2.dp))
            Text(merchant.metaLine, fontSize = 12.sp, color = Color.Gray)
        }

        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
}

@Composable
fun SearchDishRow(dish: SearchDishResult, onClick: () -> Unit) {
    val dishImage = rememberMerchantProductImage(dish.merchantName, dish.dishName, 180, 180)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            if (dishImage != null) {
                Image(
                    bitmap = dishImage,
                    contentDescription = dish.dishName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("🍴", fontSize = 24.sp)
            }
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(dish.dishName, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(2.dp))
            Text(dish.merchantName, fontSize = 13.sp, color = Color.Gray)
            Spacer(Modifier.height(2.dp))
            Text(dish.priceText, fontSize = 13.sp, color = Color.Gray)
        }

        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
}
