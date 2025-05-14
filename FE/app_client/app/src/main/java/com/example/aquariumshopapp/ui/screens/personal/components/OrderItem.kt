package com.example.aquariumshopapp.ui.screens.personal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.DELIVERING_BLUE
import com.example.aquarium_app.ui.theme.DONE_GREEN
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.WAITING_YELLOW
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.model.ProductImage

@Composable
fun OrderItem(
    navController: NavController,
    orderCode: Long,
    status: String,
    id: Long
) {
    val iconId = when(status) {
        "Đã hoàn thành" -> R.drawable.square_check_solid
        "Đang xử lý" -> R.drawable.hourglass_end_solid
        else -> R.drawable.truck_fast_solid
    }

    val iconColor = when(status) {
        "Đã hoàn thành" -> DONE_GREEN
        "Đang xử lý" -> WAITING_YELLOW
        else -> DELIVERING_BLUE
    }

    val textStatus = when(status) {
        "Đã hoàn thành" -> "đã giao thành công"
        "Đang xử lý" -> "đang chờ xử lý"
        else -> "đang được vận chuyển"
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            .clickable { navController.navigate("order_details/${id}") },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(iconId),
            contentDescription = "Image",
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier
                .size(60.dp)
                .clip(shape = RoundedCornerShape(8))
        )
        Text(
            text = "Đơn hàng (mã #$orderCode) của bạn $textStatus! Nhấn để xem chi tiết đơn hàng!",
            style = Typography.titleSmall
        )
    }
}