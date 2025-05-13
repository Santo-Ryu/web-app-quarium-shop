package com.example.aquariumshopapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.home.components.Header
import com.example.aquarium_app.ui.screens.home.components.MainContent
import com.example.aquarium_app.ui.screens.home.components.NavigationBar
import com.example.aquarium_app.ui.theme.BackgroundColor
import com.example.aquariumshopapp.ui.theme.productCardModifier

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val productImages by viewModel.productImages.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getHomeData()
    }

    /*  Background  */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
    ) {
        /*  Modifiers  */
        val mainContentModifier = Modifier
            .fillMaxWidth()
            .weight(.8f)
            .verticalScroll(rememberScrollState())

        /*  Header  */
        Header(navController)

        /*  Main  */
        MainContent(
            mainContentModifier =  mainContentModifier,
            cardModifier = Modifier.productCardModifier().weight(1f),
            navController =  navController,
            categories = categories,
            products = products,
            productImages = productImages
        )

        /*  Navigation bar  */
        NavigationBar(navController)
    }
}