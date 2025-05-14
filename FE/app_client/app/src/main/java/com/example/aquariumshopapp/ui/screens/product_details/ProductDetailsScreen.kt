package com.example.aquariumshopapp.ui.screens.product_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.model.CartItem
import com.example.aquariumshopapp.data.service.ShoppingCartService
import com.example.aquariumshopapp.ui.screens.product_details.components.ProductDetails
import kotlinx.coroutines.launch

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    productId: Long,
    viewModel: ProductDetailsViewModel = viewModel()
) {
    val product by viewModel.product.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val images by viewModel.images.collectAsState()
    val productRelated by viewModel.productRelated.collectAsState()
    val productImageRelated by viewModel.productImageRelated.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        viewModel.getData(productId)
    }

    if (product != null && !images.isNullOrEmpty()) {
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
                ProductDetails(
                    navController = navController,
                    product = product,
                    comments = comments,
                    images = images,
                    productRelated = productRelated,
                    productImageRelated = productImageRelated
                )
            }

            /*  Navbar  */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Transparent)
                    .padding(start = Dimens.paddingMedium, end = Dimens.paddingMedium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .background(BlackAlpha30, shape = CircleShape)
                        .padding(14.dp)
                        .clickable {
                            navController.navigate("home")
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrow_left_solid),
                        contentDescription = "Icon",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(Dimens.iconSizeMedium)
                    )
                }
                Box(
                    modifier = Modifier
                        .background(BlackAlpha30, shape = CircleShape)
                        .padding(14.dp)
                        .clickable {
                            coroutineScope.launch {
                                viewModel.addToShoppingCart(context)
                            }
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.cart_shopping_solid),
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