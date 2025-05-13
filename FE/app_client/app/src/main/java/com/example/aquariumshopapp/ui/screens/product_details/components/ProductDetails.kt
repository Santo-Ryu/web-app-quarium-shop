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
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage

@Composable
fun ProductDetails(
    navController: NavController,
    product: Product,
    comments: List<Comment>,
    images: List<ProductImage>,
    productRelated: List<Product>,
    productImageRelated: List<ProductImage>
) {
    val mainImage = remember { mutableStateOf(images.first()) }

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
            AsyncImage(
                model = "${RetrofitClient.BASE_URL}api/public/image?name=${imageRes.name}",
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
            val visible = product.discountPercentage == null || product.discountPercentage == 0

            /*  Sale Tags  */
            if (!visible) SaleTag(product)

            /*  Title & Price  */
            ProductTitleAndPrice(
                visible = visible,
                product = product
            )

            /*  Product Content  */
            ProductContent(
                productImages = images,
                onImageClick = { clickedImage -> mainImage.value = clickedImage },
                product = product
            )

            /*  Product Comments  */
            ProductComment(
                navController = navController,
                comments = comments,
                product = product
            )

            /*  Product Related  */
            ProductRelated(
                products = productRelated,
                productImages = productImageRelated,
                navController = navController
            )
        }
    }
}