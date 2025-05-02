package com.example.aquariumshopapp.ui.screens.product_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.ui.screens.product_details.components.ProductDetails

@Composable
fun ProductDetailsScreen() {
    Box() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Transparent)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Bottom,
        ) {
            /*  Product Details  */
            ProductDetails()
        }

        /*  Navbar  */
        val listOfNavbar = listOf(
            R.drawable.arrow_left_solid,
            R.drawable.cart_shopping_solid
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.Transparent)
                .padding(start = Dimens.paddingMedium, end = Dimens.paddingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOfNavbar.forEach { item ->
                Box(
                    modifier = Modifier
                        .background(BlackAlpha30, shape = CircleShape)
                        .padding(14.dp)
                ) {
                    Image(
                        painter = painterResource(item),
                        contentDescription = "Icon",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(Dimens.iconSizeMedium)
                    )
                }
            }
        }
    }
}