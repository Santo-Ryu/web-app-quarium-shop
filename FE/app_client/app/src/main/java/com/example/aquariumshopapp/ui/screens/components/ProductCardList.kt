package com.example.aquariumshopapp.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.home.components.ProductCard
import com.example.aquarium_app.ui.theme.*
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage
import com.example.aquariumshopapp.ui.model.TestData

@Composable
fun ProductCardList(
    productImages: List<ProductImage>,
    products: List<Product>,
    cardModifier: Modifier,
    navController: NavController
) {
    val totalItem = products.size

    Column() {
        // Hiển thị từng cặp sản phẩm trong Row
        for (rowIndex in 0 until totalItem / 2 + totalItem % 2) {
            val firstIndex = rowIndex * 2
            Row(
                modifier = Modifier
                    .padding(
                        top = Dimens.paddingXSmall,
                        start = Dimens.paddingXSmall,
                        end = Dimens.paddingXSmall
                    )
            ) {
                ProductCard(
                    modifier = cardModifier,
                    navController = navController,
                    product = products[firstIndex],
                    productImages = productImages
                )
                Spacer(modifier = Modifier.width(Dimens.paddingXSmall))
                if (firstIndex + 1 < totalItem) {
                    ProductCard(
                        modifier = cardModifier,
                        navController = navController,
                        product = products[firstIndex + 1],
                        productImages = productImages
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}