package com.example.ubereats_sim.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.model.MerchantMenuItem
import com.example.ubereats_sim.model.ProductOption

@Composable
fun ProductDetailScreen(
    merchantName: String,
    product: MerchantMenuItem,
    onAddToCart: (quantity: Int, optionCounts: Map<String, Int>) -> Unit
) {
    val onBack = LocalNavBack.current
    val productImage = rememberMerchantProductImage(merchantName, product.name, reqWidth = 1200, reqHeight = 700)
    var quantity by remember { mutableIntStateOf(1) }
    val optionCounts = remember {
        mutableStateMapOf<String, Int>().apply {
            product.options.forEach { put(it.name, 1) }
        }
    }
    val hasSpecialInstruction = remember { mutableStateOf(false) }
    val totalPrice = quantity * product.price

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            Column(modifier = Modifier.background(Color.White).padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CounterButton(
                        quantity = quantity,
                        onDecrease = { if (quantity > 1) quantity-- },
                        onIncrease = { quantity++ }
                    )
                    Button(
                        onClick = { onAddToCart(quantity, optionCounts.toMap()) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).padding(start = 10.dp)
                    ) {
                        Text(
                            "Add $quantity to cart | US$${String.format("%.2f", totalPrice)}",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            item {
                ProductTopBar(onBack = onBack)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color(0xFFEDEDED)),
                    contentAlignment = Alignment.Center
                ) {
                    if (productImage != null) {
                        Image(
                            bitmap = productImage,
                            contentDescription = product.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("IMG", fontSize = 40.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Text(merchantName, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp))
                Text(product.name, fontSize = 34.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                Text(product.priceText, fontSize = 28.sp, modifier = Modifier.padding(horizontal = 16.dp))
                Text(product.calorieText, fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
                HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
                Text(
                    "${product.name} Comes With",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }

            items(product.options) { option ->
                OptionRow(
                    option = option,
                    quantity = optionCounts[option.name] ?: 1,
                    onDecrease = {
                        val current = optionCounts[option.name] ?: 1
                        if (current > 1) optionCounts[option.name] = current - 1
                    },
                    onIncrease = {
                        val current = optionCounts[option.name] ?: 1
                        optionCounts[option.name] = current + 1
                    }
                )
            }

            item {
                HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFE0E0E0), modifier = Modifier.padding(top = 6.dp))
                Text(
                    "${product.name} Special Instructions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Substitute Bacon", fontSize = 18.sp)
                    Checkbox(
                        checked = hasSpecialInstruction.value,
                        onCheckedChange = { hasSpecialInstruction.value = it }
                    )
                }
                Spacer(Modifier.height(14.dp))
                Text("Allergy requests", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 16.dp))
                Text(
                    "Store cannot accommodate in-app food allergy requests",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ProductTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(shape = CircleShape, color = Color(0xFFF2F2F2), modifier = Modifier.clickable { onBack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.padding(8.dp))
        }
        Surface(shape = CircleShape, color = Color(0xFFF2F2F2)) {
            Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
private fun OptionRow(
    option: ProductOption,
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(option.name, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text("US$${String.format("%.2f", option.price)} ea", fontSize = 15.sp, color = Color(0xFF444444))
            Text(option.calorieText, fontSize = 15.sp, color = Color(0xFF444444))
        }
        CounterButton(quantity = quantity, onDecrease = onDecrease, onIncrease = onIncrease)
    }
}

@Composable
private fun CounterButton(quantity: Int, onDecrease: () -> Unit, onIncrease: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("-", fontSize = 22.sp, modifier = Modifier.clickable { onDecrease() })
        Text(quantity.toString(), fontSize = 18.sp, modifier = Modifier.padding(horizontal = 10.dp))
        Text("+", fontSize = 22.sp, modifier = Modifier.clickable { onIncrease() })
    }
}
