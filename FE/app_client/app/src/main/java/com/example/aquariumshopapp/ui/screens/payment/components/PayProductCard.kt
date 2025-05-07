package com.example.aquariumshopapp.ui.screens.payment.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.R

@Composable
fun PayProductCard(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.cay_dong_tien),
            contentDescription = "Product",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(70.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, BlackAlpha10, shape = RoundedCornerShape(12.dp))
        )
        Column() {
            Text(
                text = "Cây xanh xanh",
                style = Typography.titleMedium
            )
            Text(
                text = "20,000 đ",
                style = Typography.bodyLarge
            )
            Text(
                text = "Số lượng: 12",
                style = Typography.bodySmall
            )
            Text(
                text = "Giảm giá: 20% (10, 000đ)", // nếu có giảm giá thì display
                style = Typography.bodySmall
            )
        }
    }
}