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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ubereats_sim.model.MerchantCartItem
import com.example.ubereats_sim.model.MerchantMenuItem
import com.example.ubereats_sim.presenter.MerchantPresenter
import com.example.ubereats_sim.ui.theme.Test05Theme
import com.example.ubereats_sim.view.CartScreen
import com.example.ubereats_sim.view.HomeScreen
import com.example.ubereats_sim.view.LocationScreen
import com.example.ubereats_sim.view.MerchantScreen
import com.example.ubereats_sim.view.ProductDetailScreen
import com.example.ubereats_sim.view.ProfileScreen
import com.example.ubereats_sim.view.SearchScreen
import com.example.ubereats_sim.view.ViewCartScreen

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
    val context = LocalContext.current
    val merchantPresenter = remember(context) { MerchantPresenter(context) }

    fun pushPage(page: String) {
        navStack.add(page)
    }

    fun popPage() {
        if (navStack.isNotEmpty()) {
            navStack.removeAt(navStack.lastIndex)
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

    CompositionLocalProvider(
        LocalNavController provides { page -> pushPage(page) },
        LocalNavBack provides { popPage() }
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
