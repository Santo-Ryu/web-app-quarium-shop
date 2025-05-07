package com.example.aquariumshopapp.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.home.components.ProductCard
import com.example.aquarium_app.ui.theme.*
import com.example.aquarium_app.ui.theme.GreenPrimary

@Composable
fun ProductCardList(
    totalItem: Int,
    cardModifier: Modifier,
    navController: NavController
) {
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
                ProductCard(cardModifier, navController)
                Spacer(modifier = Modifier.width(Dimens.paddingXSmall))
                if (firstIndex + 1 < totalItem) {
                    ProductCard(cardModifier, navController)
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}