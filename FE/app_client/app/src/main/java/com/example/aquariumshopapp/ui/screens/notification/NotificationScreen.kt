package com.example.aquariumshopapp.ui.screens.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.home.components.NavigationBar
import com.example.aquarium_app.ui.theme.BackgroundColor
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.DELIVERING_BLUE
import com.example.aquarium_app.ui.theme.DONE_GREEN
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.RATING_YELLOW
import com.example.aquarium_app.ui.theme.RATING_YELLOW_2
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.WAITING_YELLOW
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R

@Composable
fun NotificationScreen(navController: NavController, viewModel: NotificationsViewModel = viewModel()) {
    val notifications = viewModel.notifications.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getNotifications(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        /*  Header  */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(White)
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Thông báo",
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        /*  Notification  */
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            notifications.forEach { item ->
                val iconId = when(item.status) {
                    "Đã hoàn thành" -> R.drawable.square_check_solid
                    "Đang xử lý" -> R.drawable.hourglass_end_solid
                    "Đang vận chuyển" -> R.drawable.truck_fast_solid
                    else -> {}
                }
                val notificationColor = when(item.status) {
                    "Đã hoàn thành" -> DONE_GREEN
                    "Đang xử lý" -> WAITING_YELLOW
                    "Đang vận chuyển" -> DELIVERING_BLUE
                    else -> {}
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(BackgroundColor.copy(.3f))
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("order_details/${item.orderId}")
                        },
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(iconId as Int),
                        contentDescription = "Icon",
                        colorFilter = ColorFilter.tint(notificationColor as Color),
                        modifier = Modifier.size(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Row() {
                        Text(
                            text = item.title,
                            style = Typography.titleMedium,
                            color = notificationColor
                        )
                    }
                }
            }
        }

        /*  Navigation  */
        NavigationBar(navController)
    }
}