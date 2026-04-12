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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.ubereats_sim.model.AppEventLogger
import com.example.ubereats_sim.model.AppStateStore
import com.example.ubereats_sim.model.CartItem
import com.example.ubereats_sim.presenter.MerchantPresenter
import com.example.ubereats_sim.model.DataLoader
import com.example.ubereats_sim.model.SeededCartFactory
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
import com.example.ubereats_sim.view.SendMessagesScreen
import com.example.ubereats_sim.view.WalletScreen
import com.example.ubereats_sim.view.ChooseRideScreen

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
val LocalTabSelector = compositionLocalOf<(Int) -> Unit> { {} }

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
    val favoriteNames = remember { mutableStateListOf<String>() }
    val context = LocalContext.current
    val merchantPresenter = remember(context) { MerchantPresenter(context) }
    val seededCartItems = remember(context, merchantPresenter) {
        SeededCartFactory.build(context, merchantPresenter)
    }
    val seededCartCounts = remember(context) {
        DataLoader.loadCart(context).associate { it.restaurantName to it.itemCount }
    }
    val initialOrders = remember(context) { DataLoader.loadOrders(context) }
    val restoredState = remember(context, merchantPresenter, seededCartItems, initialOrders) {
        AppStateStore.restore(
            context = context,
            defaultCartItems = seededCartItems,
            defaultOrders = initialOrders
        )
    }
    val cartItems = remember {
        mutableStateListOf<MerchantCartItem>().apply { addAll(restoredState.cartItems) }
    }
    val dynamicOrders = remember {
        mutableStateListOf<Order>().apply { addAll(restoredState.orders) }
    }
    var ridePickupLocation by remember { mutableStateOf("") }
    var rideDropoffLocation by remember { mutableStateOf("") }
    var selectedHomeTab by remember { mutableIntStateOf(0) }
    var checkoutDeliveryMode by remember { mutableStateOf("Standard") }
    var checkoutScheduledFor by remember { mutableStateOf("") }

    fun persistAppState() {
        AppStateStore.save(
            context = context,
            cartItems = cartItems.toList(),
            orders = dynamicOrders.toList()
        )
    }

    LaunchedEffect(Unit) {
        persistAppState()
    }

    fun pushPage(page: String) {
        navStack.add(page)
    }

    fun popPage() {
        if (navStack.isNotEmpty()) {
            navStack.removeAt(navStack.lastIndex)
        }
    }

    fun toggleFavorite(name: String) {
        val isNowFavorite = if (favoriteNames.contains(name)) {
            favoriteNames.remove(name)
            false
        } else {
            favoriteNames.add(name)
            true
        }
        AppEventLogger.append(
            context = context,
            action = "toggle_favorite",
            page = "home",
            extraData = mapOf(
                "merchant_name" to name,
                "favorited" to isNowFavorite
            )
        )
    }

    fun merchantCartCount(merchantName: String): Int {
        return cartItems.filter { it.merchantName == merchantName }.sumOf { it.quantity }
    }

    fun addToCart(merchantName: String, product: MerchantMenuItem, quantity: Int, selectedOptions: Map<String, Int>) {
        var updatedQuantity = quantity
        val index = cartItems.indexOfFirst { it.merchantName == merchantName && it.product.id == product.id }
        if (index >= 0) {
            val existing = cartItems[index]
            updatedQuantity = existing.quantity + quantity
            cartItems[index] = existing.copy(
                quantity = updatedQuantity,
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
        AppEventLogger.append(
            context = context,
            action = "add_to_cart",
            page = "product_detail",
            extraData = mapOf(
                "merchant_name" to merchantName,
                "item_name" to product.name,
                "quantity_added" to quantity,
                "item_quantity_in_cart" to updatedQuantity
            )
        )
        persistAppState()
    }

    fun removeFromCart(item: MerchantCartItem) {
        cartItems.remove(item)
        persistAppState()
    }

    fun updateCartItemQuantity(item: MerchantCartItem, delta: Int) {
        val index = cartItems.indexOf(item)
        if (index < 0 || delta == 0) return

        val updatedQuantity = cartItems[index].quantity + delta
        if (updatedQuantity <= 0) {
            cartItems.removeAt(index)
        } else {
            cartItems[index] = cartItems[index].copy(quantity = updatedQuantity)
        }
        persistAppState()
    }

    fun productRoute(merchantName: String, productId: String): String =
        "$RouteProduct|$merchantName|$productId"

    fun viewCartRoute(merchantName: String): String =
        "$RouteViewCart|$merchantName"

    fun openCheckout(merchantName: String) {
        checkoutDeliveryMode = "Standard"
        checkoutScheduledFor = ""
        pushPage("Checkout|$merchantName")
    }

    fun openPayment(merchantName: String) {
        val merchantItems = cartItems.filter { it.merchantName == merchantName }
        if (merchantItems.isEmpty()) return
        val orderItems = merchantItems.map { it.product.name }
        val totalQuantity = merchantItems.sumOf { it.quantity }
        AppEventLogger.append(
            context = context,
            action = "open_payment",
            page = "payment",
            extraData = mapOf(
                "merchant_name" to merchantName,
                "item_names" to orderItems,
                "total_quantity" to totalQuantity,
                "delivery_mode" to checkoutDeliveryMode,
                "scheduled_for" to checkoutScheduledFor,
                "default_delivery" to (checkoutDeliveryMode == "Standard")
            )
        )
        pushPage("Pay|$merchantName")
    }

    fun placeOrder(merchantName: String) {
        val merchantItems = cartItems.filter { it.merchantName == merchantName }
        if (merchantItems.isEmpty()) return
        val orderId = "ORD${String.format("%03d", dynamicOrders.size)}"
        val orderItems = merchantItems.map { OrderItem(it.product.name, it.quantity, it.product.price) }
        val total = merchantItems.sumOf { it.quantity * it.product.price }
        val totalQuantity = merchantItems.sumOf { it.quantity }
        val isScheduledOrder = checkoutDeliveryMode == "Schedule" && checkoutScheduledFor.isNotBlank()
        val orderStatus = if (isScheduledOrder) "Scheduled" else "In Progress"
        val estimatedArrival = if (isScheduledOrder) checkoutScheduledFor else "30 min"
        val latestArrival = if (isScheduledOrder) null else "45 min"
        val deliveryStatus = if (isScheduledOrder) "Scheduled" else "Preparing"
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
            status = orderStatus,
            items = orderItems,
            estimatedArrival = estimatedArrival,
            latestArrival = latestArrival,
            scheduledFor = checkoutScheduledFor.ifBlank { null },
            deliveryMode = checkoutDeliveryMode,
            deliveryStatus = deliveryStatus,
            driverName = "Alex",
            driverRating = "95%",
            driverVehicle = "Honda Civic",
            merchantAddress = "New York",
            deliveryAddress = "123 Main St, New York"
        )
        dynamicOrders.add(0, newOrder)
        AppEventLogger.append(
            context = context,
            action = "confirm_payment",
            page = "payment",
            extraData = mapOf(
                "merchant_name" to merchantName,
                "item_names" to orderItems.map { it.name },
                "total_quantity" to totalQuantity,
                "delivery_address" to (newOrder.deliveryAddress ?: ""),
                "default_delivery" to (checkoutDeliveryMode == "Standard"),
                "from_seeded_cart" to (seededCartCounts[merchantName] == totalQuantity),
                "delivery_mode" to checkoutDeliveryMode,
                "scheduled_for" to checkoutScheduledFor
            )
        )
        cartItems.removeAll { it.merchantName == merchantName }
        checkoutDeliveryMode = "Standard"
        checkoutScheduledFor = ""
        persistAppState()
    }

    fun buildCartSummaries(items: List<MerchantCartItem>): List<CartItem> {
        return items.groupBy { it.merchantName }.map { (merchantName, merchantItems) ->
            CartItem(
                restaurantId = merchantName.lowercase().replace(" ", "_"),
                restaurantName = merchantName,
                restaurantImage = merchantName,
                itemCount = merchantItems.sumOf { it.quantity },
                totalPrice = merchantItems.sumOf { it.quantity * it.product.price },
                deliveryAddress = "New York"
            )
        }
    }

    CompositionLocalProvider(
        LocalNavController provides { page -> pushPage(page) },
        LocalNavBack provides { popPage() },
        LocalFavorites provides Pair(favoriteNames.toSet()) { name -> toggleFavorite(name) },
        LocalOrders provides dynamicOrders.toList(),
        LocalRidePickup provides Pair(ridePickupLocation) { loc -> ridePickupLocation = loc },
        LocalRideDropoff provides Pair(rideDropoffLocation) { loc -> rideDropoffLocation = loc },
        LocalTabSelector provides { tab -> selectedTab = tab }
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
                                    onDecreaseQuantity = { item -> updateCartItemQuantity(item, -1) },
                                    onIncreaseQuantity = { item -> updateCartItemQuantity(item, 1) },
                                    onAddItems = { popPage() },
                                    onCheckout = { openCheckout(merchantName) },
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

                        currentPage.startsWith("sendmessages/") -> {
                            val orderId = currentPage.substringAfter("sendmessages/")
                            SendMessagesScreen(orderId)
                        }

                        currentPage.startsWith("order_history_detail/") -> {
                            val orderId = currentPage.substringAfter("order_history_detail/")
                            OrderHistoryDetailScreen(orderId)
                        }

                        currentPage.startsWith("Checkout|") -> {
                            val merchantName = currentPage.substringAfter("Checkout|")
                            val merchantItems = cartItems.filter { it.merchantName == merchantName }
                            CheckoutScreen(
                                merchantName = merchantName,
                                cartItems = merchantItems,
                                selectedDeliveryMode = checkoutDeliveryMode,
                                scheduledFor = checkoutScheduledFor,
                                onDeliveryModeChange = { mode, scheduledFor ->
                                    checkoutDeliveryMode = mode
                                    checkoutScheduledFor = scheduledFor
                                },
                                onNext = {
                                    openPayment(merchantName)
                                }
                            )
                        }

                        currentPage == "Checkout" -> CheckoutScreen()

                        currentPage == "Wallet" -> WalletScreen()
                        currentPage.startsWith("Pay|") -> {
                            val merchantName = currentPage.substringAfter("Pay|")
                            PaymentScreen(
                                onPlaceOrder = {
                                    placeOrder(merchantName)
                                    navStack.clear()
                                    pushPage("Orders")
                                }
                            )
                        }
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
                        currentPage == "ChooseRide" -> ChooseRideScreen()
                        currentPage == "Settings" -> SettingsScreen()
                        currentPage.startsWith("SettingsHome|") -> {
                            val initialLabel = currentPage.substringAfter("SettingsHome|").ifBlank { "Home" }
                            SettingsHomeScreen(initialLabel = initialLabel)
                        }
                        currentPage == "SettingsHome" -> SettingsHomeScreen()
                        currentPage.startsWith("SettingsHomeSet|") -> {
                            val parts = currentPage.split("|")
                            val initialLabel = parts.getOrNull(1).orEmpty().ifBlank { "Home" }
                            val initialBuildingType = parts.getOrNull(2).orEmpty().ifBlank { "House" }
                            SettingsHomeSetScreen(
                                initialLabel = initialLabel,
                                initialBuildingType = initialBuildingType
                            )
                        }
                        currentPage == "SettingsHomeSet" -> SettingsHomeSetScreen()

                        else -> UnderDevelopmentScreen(currentPage)
                    }
                } else {
                    when (selectedTab) {
                        0 -> HomeScreen(
                            selectedHomeTab = selectedHomeTab,
                            onHomeTabChanged = { selectedHomeTab = it }
                        )
                        1 -> LocationScreen()
                        2 -> SearchScreen()
                        3 -> CartScreen(cartItems = buildCartSummaries(cartItems))
                        4 -> ProfileScreen()
                    }
                }
            }
        }
    }
}
