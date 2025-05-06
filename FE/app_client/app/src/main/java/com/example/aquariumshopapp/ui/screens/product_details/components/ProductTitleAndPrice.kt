package com.example.aquariumshopapp.ui.screens.product_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.PRODUCT_NAME
import com.example.aquarium_app.ui.theme.SALE_OFF_TEXT
import com.example.aquarium_app.ui.theme.titleLarge
import com.example.aquarium_app.ui.theme.titleMedium
import com.example.aquarium_app.ui.theme.titleSmall

@Composable
fun ProductTitleAndPrice(
    visible: Boolean
) {
    /*  Rating & Sold  */
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.paddingMedium,
                end = Dimens.paddingMedium,
                top = Dimens.paddingXSmall
            )
    ) {
        Text(
            "Đã bán: 1,342 | Đánh giá 4.5",
            style = TextStyle(
                fontSize = 12.sp
            ),
            textDecoration = TextDecoration.Underline
        )
    }

    /*  Product Name & Price  */
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(
                start = Dimens.paddingMedium,
                end = Dimens.paddingMedium,
                top = Dimens.paddingXSmall
            ),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        /*  Product Name  */
        Text(
            "Rau má đồng tiền bán cạn",
            style = titleMedium,
            textAlign = TextAlign.Left,
            color = PRODUCT_NAME,
            modifier = Modifier
                .fillMaxWidth(.6f)
                .fillMaxHeight()
        )

        /*  Price  */
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End
        ) {
            if (visible) {
                Text(
                    "20,000 đ",
                    style = titleSmall,
                    color = GreenPrimary,
                    textDecoration = TextDecoration.LineThrough
                )
            }
            Text(
                "20,000 đ",
                style = titleLarge,
                color = if (visible) SALE_OFF_TEXT else GreenPrimary
            )
        }
    }
}