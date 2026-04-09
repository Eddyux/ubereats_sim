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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ubereats_sim.LocalNavBack
import com.example.ubereats_sim.LocalOrders
import com.example.ubereats_sim.model.AppEventLogger

private const val DeliveryEtaPrompt = "How long will it take to arrive?"

@Composable
fun SendMessagesScreen(orderId: String) {
    val navBack = LocalNavBack.current
    val orders = LocalOrders.current
    val context = LocalContext.current
    val order = orders.find { it.id == orderId }
    var draftMessage by rememberSaveable { mutableStateOf("") }
    val sentMessages = remember { mutableStateListOf<String>() }

    if (order == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Order not found")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column {
                Text("Send message", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Contact ${order.driverName ?: "your courier"}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        HorizontalDivider(color = Color(0xFFE6E6E6))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Suggested question",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.clickable { draftMessage = DeliveryEtaPrompt }
            ) {
                Text(
                    DeliveryEtaPrompt,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = draftMessage,
                onValueChange = { draftMessage = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                placeholder = { Text("Type your message") },
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val message = draftMessage.trim()
                    if (message.isNotEmpty()) {
                        sentMessages.add(message)
                        AppEventLogger.append(
                            context = context,
                            action = "send_message",
                            page = "sendmessages",
                            extraData = mapOf(
                                "order_id" to order.id,
                                "merchant_name" to order.merchantName,
                                "driver_name" to (order.driverName ?: ""),
                                "message" to message
                            )
                        )
                        draftMessage = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Send")
            }
        }

        if (sentMessages.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("No messages yet", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sentMessages) { message ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Spacer(modifier = Modifier.width(48.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF2D5F2E)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = message,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
