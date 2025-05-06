package com.example.aquarium_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aquarium_app.ui.screens.auth.AuthScreen
import com.example.aquariumshopapp.ui.screens.home.HomeScreen
import com.example.aquariumshopapp.ui.screens.product_details.ProductDetailsScreen

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
        startDestination = "product_details"
    ) {
        composable("login") { AuthScreen() }
        composable("home") { HomeScreen() }
        composable("product_details") { ProductDetailsScreen() }
    }
}