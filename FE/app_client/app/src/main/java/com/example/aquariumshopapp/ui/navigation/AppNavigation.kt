package com.example.aquarium_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aquarium_app.ui.screens.auth.AuthScreen
import com.example.aquariumshopapp.ui.screens.home.HomeScreen
import com.example.aquariumshopapp.ui.screens.notification.NotificationScreen
import com.example.aquariumshopapp.ui.screens.payment.PaymentScreen
import com.example.aquariumshopapp.ui.screens.personal.ChangePasswordScreen
import com.example.aquariumshopapp.ui.screens.personal.MessageScreen
import com.example.aquariumshopapp.ui.screens.personal.OrderDetailScreen
import com.example.aquariumshopapp.ui.screens.personal.OrderHistory
import com.example.aquariumshopapp.ui.screens.personal.PersonalInfoScreen
import com.example.aquariumshopapp.ui.screens.personal.PersonalScreen
import com.example.aquariumshopapp.ui.screens.personal.PersonalUpdateScreen
import com.example.aquariumshopapp.ui.screens.product_details.ProductDetailsScreen
import com.example.aquariumshopapp.ui.screens.product_review.ProductReviewScreen
import com.example.aquariumshopapp.ui.screens.search.FilterSideBar
import com.example.aquariumshopapp.ui.screens.search.SearchInputScreen
import com.example.aquariumshopapp.ui.screens.search.SearchResultScreen
import com.example.aquariumshopapp.ui.screens.shopping_cart.ShoppingCartScreen

@Composable
fun AppNavigation() {
    /*  Switch between screens
    *   Save the previous screen to NavBackStack
    * */
    val navController = rememberNavController()

    /*  Login status  */
    val isLoggedIn = remember { mutableStateOf(false) }

    /*  Navigation to Composes
    *   Start screen when running the App by "startDestination"
    * */
    NavHost(
        navController = navController,
//        startDestination = if (isLoggedIn.value) "home" else "login"
        startDestination = "home"
    ) {
        composable("auth") { AuthScreen(navController) }

        composable("home") { HomeScreen(navController) }

        composable("product_details") { ProductDetailsScreen(navController) }

        composable("product_review") { ProductReviewScreen(navController) }

        composable("search_input") { SearchInputScreen(navController) }

        composable("search_result") { SearchResultScreen(navController) }

        composable("filter_sidebar") { FilterSideBar(navController) }

        composable("shopping_cart") { ShoppingCartScreen(navController) }

        composable("payment") { PaymentScreen(navController) }

        composable("notification") { NotificationScreen(navController) }

        composable("personal") { PersonalScreen(navController) }

        composable("personal_info") { PersonalInfoScreen(navController) }

        composable("personal_update/{fieldNavigate}") { backStackEntry ->
            val fieldNavigate = backStackEntry.arguments?.getString("fieldNavigate") ?: ""
            PersonalUpdateScreen(navController)
        }

        composable("change_password") { ChangePasswordScreen(navController) }

        composable("message") { MessageScreen(navController) }

        composable("order_history") { OrderHistory(navController) }

        composable("order_details") { OrderDetailScreen(navController) }
    }
}