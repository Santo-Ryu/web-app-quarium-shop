package com.example.aquarium_app.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.textButtonSmall
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.model.Category
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage
import com.example.aquariumshopapp.ui.screens.components.LabelList
import com.example.aquariumshopapp.ui.screens.components.ProductCardList
import com.example.aquariumshopapp.ui.screens.home.components.SaleOff
import com.example.aquariumshopapp.ui.model.TestData

@Composable
fun MainContent(
    mainContentModifier: Modifier,
    cardModifier: Modifier,
    navController: NavController,
    categories: List<Category>,
    products: List<Product>,
    productImages: List<ProductImage>,
) {
    val saleProducts = products.filter { it?.discountPercentage ?: 0 > 0 }

    Column (
        modifier = mainContentModifier,
    ) {
        /*  Banner  */
        SlideShow()

        /*  Sale off  */
        if (!saleProducts.isEmpty()) {
            SaleOff(
                navController = navController,
                saleProducts = saleProducts,
                productImages = productImages
            )
        }

        /*  Sort categories  */
        LabelList(categories, navController)

        /*  Product list  */
        ProductCardList(
            productImages = productImages,
            products = products.take(10),
            cardModifier = cardModifier,
            navController
        )

        // Nút "Xem thêm"
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.paddingXSmall,
                    start = Dimens.paddingXSmall,
                    end = Dimens.paddingXSmall,
                    bottom = Dimens.paddingXSmall
                )
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary
            )
        ) {
            Text(
                text = "Xem thêm",
                style = textButtonSmall,
                modifier = Modifier
                    .background(Color.Transparent)
            )
        }
    }
}