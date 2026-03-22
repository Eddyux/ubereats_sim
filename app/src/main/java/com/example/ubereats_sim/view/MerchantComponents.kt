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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.model.MerchantMenuItem
import com.example.ubereats_sim.model.MerchantSummary

@Composable
fun MerchantHero(
    merchantName: String,
    onBack: () -> Unit,
    onSearch: () -> Unit,
    onFavorite: () -> Unit,
    onMore: () -> Unit
) {
    val heroImage = rememberMerchantImage(merchantName, reqWidth = 1400, reqHeight = 500)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .background(Color(0xFFEFEFEF))
    ) {
        if (heroImage != null) {
            Image(
                bitmap = heroImage,
                contentDescription = merchantName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIconButton(icon = { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }, onClick = onBack)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CircleIconButton(icon = { Icon(Icons.Default.Search, contentDescription = "Search") }, onClick = onSearch)
                CircleIconButton(icon = { Text("Fav", fontSize = 13.sp) }, onClick = onFavorite)
                CircleIconButton(icon = { Icon(Icons.Default.MoreVert, contentDescription = "More") }, onClick = onMore)
            }
        }
    }
}

@Composable
private fun CircleIconButton(icon: @Composable () -> Unit, onClick: () -> Unit) {
    Surface(modifier = Modifier.clickable { onClick() }, shape = CircleShape, color = Color(0xFFE8E8E8)) {
        Box(modifier = Modifier.padding(9.dp), contentAlignment = Alignment.Center) { icon() }
    }
}

@Composable
fun MerchantBrand(summary: MerchantSummary) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            MerchantBrandAvatar(summary.name)
        }
        Spacer(Modifier.height(8.dp))
        Text(summary.name, fontWeight = FontWeight.Bold, fontSize = 34.sp, modifier = Modifier.fillMaxWidth())
        Text(
            "${summary.rating} * ${summary.reviewCountText} | ${summary.badge} | ${summary.distanceText}",
            fontSize = 14.sp,
            color = Color(0xFF555555)
        )
        Text("- ${summary.prepTimeText}", fontSize = 14.sp, color = Color(0xFF555555), modifier = Modifier.padding(top = 2.dp))
        Spacer(Modifier.height(14.dp))
    }
}

@Composable
private fun MerchantBrandAvatar(merchantName: String) {
    val image = rememberMerchantImage(merchantName, reqWidth = 256, reqHeight = 256)
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(0xFFFFC400)),
        contentAlignment = Alignment.Center
    ) {
        if (image != null) {
            Image(
                bitmap = image,
                contentDescription = merchantName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            val brandMark = merchantName.firstOrNull()?.uppercase() ?: "?"
            Text(brandMark, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Red)
        }
    }
}

@Composable
fun MerchantActionRow(onDelivery: () -> Unit, onPickup: () -> Unit, onGroupOrder: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ActionChip("Delivery", selected = true, modifier = Modifier.weight(1f), onClick = onDelivery)
        ActionChip("Pickup", selected = false, modifier = Modifier.weight(1f), onClick = onPickup)
        ActionChip("Group order", selected = false, modifier = Modifier.weight(1f), onClick = onGroupOrder)
    }
}

@Composable
private fun ActionChip(label: String, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        color = if (selected) Color(0xFFE7E7E7) else Color(0xFFF5F5F5)
    ) {
        Text(
            label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            color = Color.Black
        )
    }
}

@Composable
fun DeliveryInfoRow(feeText: String, etaText: String, subtitle: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier.weight(1f)) {
            Text(feeText, fontSize = 13.sp, color = Color(0xFF8A6B00), fontWeight = FontWeight.Bold)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            Text(etaText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Earliest arrival", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun PromoCard(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5DBD8)
    ) {
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("*", fontSize = 18.sp)
            Spacer(Modifier.width(8.dp))
            Text("$5 off $20+", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FeatureItemRow(item: MerchantMenuItem, onAdd: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).clickable { onAdd() }) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(item.priceText, fontSize = 20.sp, color = Color(0xFF444444))
            Text(item.calorieText, fontSize = 14.sp, color = Color.Gray)
            if (item.badgeText != null) {
                Text(
                    item.badgeText,
                    color = Color.White,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 4.dp).background(Color(0xFFE11D48), RoundedCornerShape(6.dp)).padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
        AddBadge(onAdd = onAdd)
    }
}

@Composable
fun MenuItemCard(
    merchantName: String,
    item: MerchantMenuItem,
    modifier: Modifier,
    onAdd: () -> Unit,
    onOpen: () -> Unit
) {
    val productImage = rememberMerchantProductImage(merchantName, item.name, reqWidth = 600, reqHeight = 400)
    Card(modifier = modifier.clickable { onOpen() }, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)) {
        Box(
            modifier = Modifier.fillMaxWidth().height(110.dp).background(Color(0xFFEFEFEF), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.BottomEnd
        ) {
            if (productImage != null) {
                Image(
                    bitmap = productImage,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp))
                )
            } else {
                Text("IMG", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))
            }
            CircleAddButton(onAdd)
        }
        if (item.badgeText != null) {
            Text(
                item.badgeText,
                color = Color.White,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 8.dp).background(Color(0xFFE11D48), RoundedCornerShape(6.dp)).padding(horizontal = 5.dp, vertical = 2.dp)
            )
        } else {
            Spacer(Modifier.height(8.dp))
        }
        Text(item.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(item.priceText, fontSize = 15.sp, color = Color(0xFF444444), modifier = Modifier.padding(top = 2.dp))
        Text(item.calorieText, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun MerchantListItem(
    merchantName: String,
    item: MerchantMenuItem,
    onClick: () -> Unit,
    onAdd: () -> Unit
) {
    val productImage = rememberMerchantProductImage(merchantName, item.name, reqWidth = 300, reqHeight = 300)
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Text(item.priceText, fontSize = 14.sp, color = Color(0xFF444444))
            Text(item.calorieText, fontSize = 12.sp, color = Color.Gray)
        }
        Box(
            modifier = Modifier.width(92.dp).height(76.dp).background(Color(0xFFEEEEEE), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.BottomEnd
        ) {
            if (productImage != null) {
                Image(
                    bitmap = productImage,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp))
                )
            }
            CircleAddButton(onAdd)
        }
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE9E9E9))
}

@Composable
private fun AddBadge(onAdd: () -> Unit) {
    Box(
        modifier = Modifier.width(92.dp).height(76.dp).background(Color(0xFFEEEEEE), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text("IMG", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))
        CircleAddButton(onAdd)
    }
}

@Composable
private fun CircleAddButton(onAdd: () -> Unit) {
    Surface(
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = Modifier.padding(8.dp).clickable { onAdd() }
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.padding(5.dp))
    }
}
