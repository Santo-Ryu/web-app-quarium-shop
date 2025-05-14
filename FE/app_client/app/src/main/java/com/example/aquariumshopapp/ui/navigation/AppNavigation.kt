package com.example.aquarium_app.ui.navigation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aquarium_app.ui.screens.auth.AuthScreen
import com.example.aquariumshopapp.data.datastore.TokenDataStore
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
fun AppNavigation(context: Context) {
    /*  Switch between screens
    *   Save the previous screen to NavBackStack
    * */
    val navController = rememberNavController()

    /*  Navigation to Composes
    *   Start screen when running the App by "startDestination"
    * */
    NavHost(
        navController = navController,
//        startDestination = if (isLoggedIn.value) "home" else "login"
        startDestination = "auth"
    ) {
        composable("auth") { AuthScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable(
            route = "product_details/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            id?.let {
                ProductDetailsScreen(navController, it)
            }
        }
        composable(
            route = "product_review/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            id?.let {
                ProductReviewScreen(navController, it)
            }
        }
        composable("search_input") { SearchInputScreen(navController) }
        composable(
            route = "search_result/{input}/{type}",
            arguments = listOf(
                navArgument("input") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val input = backStackEntry.arguments?.getString("input") ?: ""
            val type = backStackEntry.arguments?.getString("type") ?: ""
            SearchResultScreen(navController, input, type)
        }
        composable(
            route = "filter_sidebar/{input}/{type}",
            arguments = listOf(
                navArgument("input") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val input = backStackEntry.arguments?.getString("input") ?: ""
            val type = backStackEntry.arguments?.getString("type") ?: ""
            FilterSideBar(navController, input, type)
        }
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
        composable(
            route = "order_details/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            id?.let {
                OrderDetailScreen(navController, it)
            }
        }
    }
}