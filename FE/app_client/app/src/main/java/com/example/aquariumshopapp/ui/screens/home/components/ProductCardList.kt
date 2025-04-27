package com.example.aquariumshopapp.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.screens.home.components.ProductCard
import com.example.aquarium_app.ui.theme.*
import com.example.aquarium_app.ui.theme.GreenPrimary

fun LazyListScope.ProductCardList(
    totalItem: Int,
    cardModifier: Modifier
) {
    items(totalItem / 2 + totalItem % 2) { rowIndex ->
        val firstIndex = rowIndex * 2
        Row (
            modifier = Modifier
                .padding(
                    top = Dimens.paddingXSmall,
                    start = Dimens.paddingXSmall,
                    end = Dimens.paddingXSmall
                )
        ) {
            ProductCard(cardModifier)
            Spacer(modifier = Modifier.width(Dimens.paddingXSmall))
            if (firstIndex + 1 < totalItem) {
                ProductCard(cardModifier)
            }else Spacer(modifier = Modifier.weight(1f))
        }
    }

    item {
        androidx.compose.material3.Button(
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