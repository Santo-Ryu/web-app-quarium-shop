package com.example.aquariumshopapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.home.components.Header
import com.example.aquarium_app.ui.screens.home.components.MainContent
import com.example.aquarium_app.ui.screens.home.components.NavigationBar
import com.example.aquarium_app.ui.theme.BackgroundColor
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.ui.theme.productCardModifier

@Composable
fun HomeScreen(navController: NavController) {
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
            mainContentModifier,
            Modifier.productCardModifier()
                .weight(1f),
            navController
        )

        /*  Navigation bar  */
        NavigationBar(navController)
    }
}