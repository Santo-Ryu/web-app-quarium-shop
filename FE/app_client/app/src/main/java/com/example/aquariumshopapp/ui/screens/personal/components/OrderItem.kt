package com.example.aquariumshopapp.ui.screens.personal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.R

@Composable
fun OrderItem(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            .clickable { navController.navigate("order_details") },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.beta1),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(shape = RoundedCornerShape(8))
        )
        Text(
            text = "Đơn hàng #81288 của bạn đã giao thành công! Nhấn để xem chi tiết đơn hàng!",
            style = Typography.titleSmall
        )
    }
}