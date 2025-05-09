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
import com.example.aquariumshopapp.ui.model.TestData

@Composable
fun ProductCardList(
    dataList: List<TestData>,
    cardModifier: Modifier,
    navController: NavController
) {
    val totalItem = dataList.size

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
                    product = dataList[firstIndex]
                )
                Spacer(modifier = Modifier.width(Dimens.paddingXSmall))
                if (firstIndex + 1 < totalItem) {
                    ProductCard(
                        modifier = cardModifier,
                        navController = navController,
                        product = dataList[firstIndex + 1]
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}