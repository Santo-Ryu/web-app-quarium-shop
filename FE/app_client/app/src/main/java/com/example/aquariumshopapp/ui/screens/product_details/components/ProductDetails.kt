package com.example.aquariumshopapp.ui.screens.product_details.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquariumshopapp.R

@Composable
fun ProductDetails(navController: NavController) {
    val productImages = listOf(
        R.drawable.cay_dong_tien,
        R.drawable.banner3,
        R.drawable.cay_dong_tien,
        R.drawable.cay_dong_tien,
        R.drawable.cay_dong_tien,
    )
    val mainImage = remember { mutableStateOf(productImages.first()) }

    /*  Product Details  */
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Bottom,
    ) {
        /*  Main Product Image  */
        Crossfade(targetState = mainImage.value) { imageRes ->
            Image(
                painter = painterResource(imageRes),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .border(1.dp, BlackAlpha10)
            )
        }

        /*  Content  */
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val visible = false

            /*  Sale Tags  */
            if (visible) SaleTag()

            /*  Title & Price  */
            ProductTitleAndPrice(visible)

            /*  Product Content  */
            ProductContent(
                productImages,
                onImageClick = { clickedImage ->
                    mainImage.value = clickedImage
                }
            )

            /*  Product Comments  */
            ProductComment(navController)

            /*  Product Related  */
            ProductRelated()
        }
    }
}