package com.example.aquariumshopapp.ui.screens.product_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.SALE_OFF_BG
import com.example.aquarium_app.ui.theme.White
import com.example.aquarium_app.ui.theme.titleSmall
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.ui.utils.ValidateUtils

@Composable
fun SaleTag(
    product: Product
) {
    Row(
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
            .background(SALE_OFF_BG),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = Dimens.paddingMedium
            )
        ) {
            Image(
                painter = painterResource(R.drawable.tags_solid),
                contentDescription = "Sale Off",
                colorFilter = ColorFilter.tint(White),
                modifier = Modifier.size(Dimens.iconSizeMedium)
            )
            Spacer(modifier = Modifier.width(Dimens.paddingSmall))
            Text(
                "Giảm giá",
                style = titleSmall,
                color = White
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                end = Dimens.paddingMedium
            )
        ) {
            Text(
                text = "Đến ngày ${ValidateUtils.formatDate(product.discountEndDate.toString())}",
                style = titleSmall,
                color = White,
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.width(Dimens.paddingSmall))
            Image(
                painter = painterResource(R.drawable.clock_solid),
                contentDescription = "Sale Off",
                colorFilter = ColorFilter.tint(White),
                modifier = Modifier.size(Dimens.iconSizeMedium)
            )
        }
    }
}