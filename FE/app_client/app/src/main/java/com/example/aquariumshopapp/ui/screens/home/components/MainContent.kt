package com.example.aquarium_app.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.*
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquariumshopapp.ui.screens.home.components.CategoryList
import com.example.aquariumshopapp.ui.screens.home.components.ProductCardList
import com.example.aquariumshopapp.ui.screens.home.components.SaleOff

@Composable
fun MainContent(
    mainContentModifier: Modifier,
    cardModifier: Modifier,
    navController: NavController
) {
    Column (
        modifier = mainContentModifier
    ) {
        /*  Banner  */
        SlideShow()

        /*  Sale off  */
        SaleOff(navController)

        /*  Sort categories  */
        CategoryList(navController)

        /*  Product list  */
        val totalItem = 10
        ProductCardList(
            totalItem = totalItem,
            cardModifier = cardModifier,
            navController
        )
    }
}