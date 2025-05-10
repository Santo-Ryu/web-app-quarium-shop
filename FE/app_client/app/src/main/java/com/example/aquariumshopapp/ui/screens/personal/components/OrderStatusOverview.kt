package com.example.aquariumshopapp.ui.screens.personal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.ui.screens.personal.model.OrderStatusItem

@Composable
fun OrderStatusOverview(
    orderState: List<OrderStatusItem>
) {
    orderState.forEach { item ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BlackAlpha10)
                .padding(Dimens.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(item.iconId),
                    contentDescription = "Icon",
                    colorFilter = ColorFilter.tint(item.color),
                    modifier = Modifier.size(Dimens.paddingLarge)
                )
                Text(
                    text = item.title,
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = item.color
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${item.totalOrder} đơn hàng",
                    color = item.color,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}