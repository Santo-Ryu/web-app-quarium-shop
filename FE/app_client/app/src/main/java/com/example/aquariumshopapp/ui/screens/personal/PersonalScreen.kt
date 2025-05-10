package com.example.aquariumshopapp.ui.screens.personal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.home.components.NavigationBar
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.DELIVERING_BLUE
import com.example.aquarium_app.ui.theme.DONE_GREEN
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.WAITING_YELLOW
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.ui.screens.personal.model.FieldsInfoItem
import com.example.aquariumshopapp.ui.screens.personal.components.FieldsInformation
import com.example.aquariumshopapp.ui.screens.personal.components.OrderStatusOverview
import com.example.aquariumshopapp.ui.screens.personal.model.OrderStatusItem

@Composable
fun PersonalScreen(navController: NavController) {
    val fields = listOf<FieldsInfoItem>(
        FieldsInfoItem("Thông tin cá nhân", "personal_info"),
        FieldsInfoItem("Nhắn tin", "message"),
        FieldsInfoItem("Thay đổi mật khẩu", "change_password"),
        FieldsInfoItem("Lịch sử mua hàng", "order_history",),
    )

    val orderStatus = listOf(
        OrderStatusItem(R.drawable.square_check_solid, DONE_GREEN, "Đang giao", 4, ""),
        OrderStatusItem(R.drawable.square_check_solid, WAITING_YELLOW, "Đang giao", 5, ""),
        OrderStatusItem(R.drawable.square_check_solid, DELIVERING_BLUE, "Đang giao", 8, ""),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
                .background(White)
                .padding(start = 15.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.avt1),
                contentScale = ContentScale.Crop,
                contentDescription = "Avatar",
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .width(60.dp)
                    .height(60.dp)
                    .border(1.dp, BlackAlpha10, shape = CircleShape)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Text(
                    text = "Santo Le",
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "5 đơn đang giao",
                    color = GreenPrimary
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            FieldsInformation(fields, navController)
            OrderStatusOverview(orderStatus)
        }

        NavigationBar(navController)
    }
}