package com.example.ubereats_sim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ubereats_sim.model.MerchantCartItem
import com.example.ubereats_sim.model.MerchantMenuItem
import com.example.ubereats_sim.model.Order
import com.example.ubereats_sim.model.OrderItem
import com.example.ubereats_sim.presenter.MerchantPresenter
import com.example.ubereats_sim.model.DataLoader
import com.example.ubereats_sim.ui.theme.Test05Theme
import com.example.ubereats_sim.view.AccessibilityScreen
import com.example.ubereats_sim.view.CartScreen
import com.example.ubereats_sim.view.CheckoutScreen
import com.example.ubereats_sim.view.HearingScreen
import com.example.ubereats_sim.view.HomeScreen
import com.example.ubereats_sim.view.LocationScreen
import com.example.ubereats_sim.view.MerchantScreen
import com.example.ubereats_sim.view.MyFavoritesScreen
import com.example.ubereats_sim.view.OrderDetailScreen
import com.example.ubereats_sim.view.OrderHistoryDetailScreen
import com.example.ubereats_sim.view.OrdersScreen
import com.example.ubereats_sim.view.PaymentScreen
import com.example.ubereats_sim.view.PrivacyLiveLocationScreen
import com.example.ubereats_sim.view.PrivacyScreen
import com.example.ubereats_sim.view.ProductDetailScreen
import com.example.ubereats_sim.view.ProfileScreen
import com.example.ubereats_sim.view.PromotionsScreen
import com.example.ubereats_sim.view.SearchScreen
import com.example.ubereats_sim.view.ViewCartScreen
import com.example.ubereats_sim.view.RideLocationScreen
import com.example.ubereats_sim.view.SettingsHomeScreen
import com.example.ubereats_sim.view.SettingsHomeSetScreen
import com.example.ubereats_sim.view.SettingsScreen
import com.example.ubereats_sim.view.WalletScreen

private val MerchantPages = setOf(
    "McDonald's",
    "VINEYARD",
    "Yunnan Rice Noodle",
    "Benvenuto Cafe",
    "Burger King",
    "7-Eleven",
    "Matchaful",
    "HAWA SMOOTHIES",
    "Domino's"
)

private const val RouteProduct = "product"
private const val RouteViewCart = "viewcart"
private const val RouteMerchant = "merchant"

val LocalNavController = compositionLocalOf<(String) -> Unit> { {} }
val LocalNavBack = compositionLocalOf<() -> Unit> { {} }
val LocalFavorites = compositionLocalOf<Pair<Set<String>, (String) -> Unit>> { Pair(emptySet()) {} }
val LocalOrders = compositionLocalOf<List<Order>> { emptyList() }
val LocalRidePickup = compositionLocalOf<Pair<String, (String) -> Unit>> { Pair("") {} }
val LocalRideDropoff = compositionLocalOf<Pair<String, (String) -> Unit>> { Pair("") {} }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test05Theme { MainScreen() }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val navStack = remember { mutableStateListOf<String>() }
    val cartItems = remember { mutableStateListOf<MerchantCartItem>() }
    val favoriteNames = remember { mutableStateListOf<String>() }
    val context = LocalContext.current
    val merchantPresenter = remember(context) { MerchantPresenter(context) }
    val initialOrders = remember(context) { DataLoader.loadOrders(context) }
    val dynamicOrders = remember { mutableStateListOf<Order>().apply { addAll(initialOrders) } }
    var ridePickupLocation by remember { mutableStateOf("") }
    var rideDropoffLocation by remember { mutableStateOf("") }

    fun pushPage(page: String) {
        navStack.add(page)
    }

    fun popPage() {
        if (navStack.isNotEmpty()) {
            navStack.removeAt(navStack.lastIndex)
        }
    }

    fun toggleFavorite(name: String) {
        if (favoriteNames.contains(name)) {
            favoriteNames.remove(name)
        } else {
            favoriteNames.add(name)
        }
    }

    fun merchantCartCount(merchantName: String): Int {
        return cartItems.filter { it.merchantName == merchantName }.sumOf { it.quantity }
    }

    fun addToCart(merchantName: String, product: MerchantMenuItem, quantity: Int, selectedOptions: Map<String, Int>) {
        val index = cartItems.indexOfFirst { it.merchantName == merchantName && it.product.id == product.id }
        if (index >= 0) {
            val existing = cartItems[index]
            cartItems[index] = existing.copy(
                quantity = existing.quantity + quantity,
                selectedOptions = selectedOptions
            )
        } else {
            cartItems.add(
                MerchantCartItem(
                    merchantName = merchantName,
                    product = product,
                    quantity = quantity,
                    selectedOptions = selectedOptions
                )
            )
        }
    }

    fun productRoute(merchantName: String, productId: String): String =
        "$RouteProduct|$merchantName|$productId"

    fun viewCartRoute(merchantName: String): String =
        "$RouteViewCart|$merchantName"

    fun placeOrder(merchantName: String) {
        val merchantItems = cartItems.filter { it.merchantName == merchantName }
        if (merchantItems.isEmpty()) return
        val orderId = "ORD${String.format("%03d", dynamicOrders.size)}"
        val orderItems = merchantItems.map { OrderItem(it.product.name, it.quantity, it.product.price) }
        val total = merchantItems.sumOf { it.quantity * it.product.price }
        val emoji = when {
            merchantName.contains("McDonald", ignoreCase = true) -> "🍔"
            merchantName.contains("Domino", ignoreCase = true) -> "🍕"
            merchantName.contains("Starbucks", ignoreCase = true) -> "☕"
            merchantName.contains("Burger King", ignoreCase = true) -> "🍔"
            else -> "🍽️"
        }
        val newOrder = Order(
            id = orderId,
            merchantName = merchantName,
            merchantLogo = emoji,
            orderDate = "2026-03-24",
            orderTime = java.text.SimpleDateFormat("HH:mm", java.util.Locale.US).format(java.util.Date()),
            totalAmount = total,
            status = "In Progress",
            items = orderItems,
            estimatedArrival = "30 min",
            latestArrival = "45 min",
            deliveryStatus = "Preparing",
            driverName = "Alex",
            driverRating = "95%",
            driverVehicle = "Honda Civic",
            merchantAddress = "New York",
            deliveryAddress = "123 Main St, New York"
        )
        dynamicOrders.add(0, newOrder)
        cartItems.removeAll { it.merchantName == merchantName }
    }

    CompositionLocalProvider(
        LocalNavController provides { page -> pushPage(page) },
        LocalNavBack provides { popPage() },
        LocalFavorites provides Pair(favoriteNames.toSet()) { name -> toggleFavorite(name) },
        LocalOrders provides dynamicOrders.toList(),
        LocalRidePickup provides Pair(ridePickupLocation) { loc -> ridePickupLocation = loc },
        LocalRideDropoff provides Pair(rideDropoffLocation) { loc -> rideDropoffLocation = loc }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (navStack.isEmpty()) {
                    val totalCartCount = cartItems.sumOf { it.quantity }
                    BottomNavigationBar(selectedTab, totalCartCount) { selectedTab = it }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                val currentPage = navStack.lastOrNull()

                if (currentPage != null) {
                    when {
                        currentPage.startsWith("$RouteProduct|") -> {
                            val parts = currentPage.split("|")
                            val merchantName = parts.getOrNull(1)
                            val productId = parts.getOrNull(2)
                            val product = if (merchantName != null && productId != null) {
                                merchantPresenter.getProductById(merchantName, productId)
                            } else {
                                null
                            }

                            if (merchantName != null && product != null) {
                                ProductDetailScreen(
                                    merchantName = merchantName,
                                    product = product,
                                    onAddToCart = { quantity, options ->
                                        addToCart(merchantName, product, quantity, options)
                                        popPage()
                                    }
                                )
                            } else {
                                UnderDevelopmentScreen("Product")
                            }
                        }

                        currentPage.startsWith("$RouteViewCart|") -> {
                            val merchantName = currentPage.split("|").getOrNull(1)
                            if (merchantName != null) {
                                val merchantItems = cartItems.filter { it.merchantName == merchantName }
                                ViewCartScreen(
                                    merchantName = merchantName,
                                    items = merchantItems,
                                    onClose = { popPage() },
                                    onRemove = { item -> cartItems.remove(item) },
                                    onReplace = { item -> pushPage(productRoute(merchantName, item.product.id)) },
                                    onAddItems = { popPage() },
                                    onCheckout = { pushPage("Checkout|$merchantName") },
                                    onOpenOfferItem = {
                                        val offer = merchantPresenter.getMerchantProducts(merchantName).firstOrNull()
                                        if (offer != null) {
                                            pushPage(productRoute(merchantName, offer.id))
                                        }
                                    }
                                )
                            } else {
                                UnderDevelopmentScreen("View cart")
                            }
                        }

                        currentPage.startsWith("$RouteMerchant|") -> {
                            val merchantName = currentPage.split("|").getOrNull(1)
                            if (merchantName != null) {
                                MerchantScreen(
                                    restaurantName = merchantName,
                                    cartCount = merchantCartCount(merchantName),
                                    onOpenProduct = { item -> pushPage(productRoute(merchantName, item.id)) },
                                    onOpenCart = { pushPage(viewCartRoute(merchantName)) }
                                )
                            } else {
                                UnderDevelopmentScreen("Merchant")
                            }
                        }

                        MerchantPages.contains(currentPage) -> {
                            MerchantScreen(
                                restaurantName = currentPage,
                                cartCount = merchantCartCount(currentPage),
                                onOpenProduct = { item -> pushPage(productRoute(currentPage, item.id)) },
                                onOpenCart = { pushPage(viewCartRoute(currentPage)) }
                            )
                        }

                        currentPage.startsWith("order_detail/") -> {
                            val orderId = currentPage.substringAfter("order_detail/")
                            OrderDetailScreen(orderId)
                        }

                        currentPage.startsWith("order_history_detail/") -> {
                            val orderId = currentPage.substringAfter("order_history_detail/")
                            OrderHistoryDetailScreen(orderId)
                        }

                        currentPage.startsWith("Checkout|") -> {
                            val merchantName = currentPage.substringAfter("Checkout|")
                            CheckoutScreen(
                                onPlaceOrder = {
                                    placeOrder(merchantName)
                                    // Pop back to home and switch to orders
                                    navStack.clear()
                                    pushPage("Orders")
                                }
                            )
                        }

                        currentPage == "Checkout" -> CheckoutScreen()

                        currentPage == "Wallet" -> WalletScreen()
                        currentPage == "Pay" -> PaymentScreen()
                        currentPage == "Promotions" -> PromotionsScreen()
                        currentPage == "Privacy" -> PrivacyScreen()
                        currentPage == "Live location" -> PrivacyLiveLocationScreen()
                        currentPage == "Favorites" -> MyFavoritesScreen(favoriteNames.toList())
                        currentPage == "Orders" -> OrdersScreen()
                        currentPage == "Accessibility" -> AccessibilityScreen()
                        currentPage == "Hearing" -> HearingScreen()
                        currentPage == "Pickup location" -> RideLocationScreen(isPickup = true)
                        currentPage == "Dropoff location" -> RideLocationScreen(isPickup = false)
                        currentPage == "Settings" -> SettingsScreen()
                        currentPage == "SettingsHome" -> SettingsHomeScreen()
                        currentPage == "SettingsHomeSet" -> SettingsHomeSetScreen()

                        else -> UnderDevelopmentScreen(currentPage)
                    }
                } else {
                    when (selectedTab) {
                        0 -> HomeScreen()
                        1 -> LocationScreen()
                        2 -> SearchScreen()
                        3 -> CartScreen()
                        4 -> ProfileScreen()
                    }
                }
            }
        }
    }
}
