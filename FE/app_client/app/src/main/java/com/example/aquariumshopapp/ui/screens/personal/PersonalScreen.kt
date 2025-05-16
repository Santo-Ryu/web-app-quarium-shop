package com.example.aquariumshopapp.ui.screens.personal

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.screens.home.components.NavigationBar
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.DELIVERING_BLUE
import com.example.aquarium_app.ui.theme.DONE_GREEN
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.WAITING_YELLOW
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.ui.screens.personal.model.FieldsInfoItem
import com.example.aquariumshopapp.ui.screens.personal.components.FieldsInformation
import com.example.aquariumshopapp.ui.screens.personal.components.OrderStatusOverview
import com.example.aquariumshopapp.ui.screens.personal.model.OrderStatusItem
import com.example.aquariumshopapp.ui.screens.personal.PersonalViewModel
import kotlinx.coroutines.launch

@Composable
fun PersonalScreen(navController: NavController, viewModel: PersonalViewModel = viewModel()) {
    val context = LocalContext.current
    val customer = viewModel.customer.collectAsState().value
    val orders = viewModel.orders.collectAsState().value
    val orderItems = viewModel.orderItems.collectAsState().value
    val categories = viewModel.categories.collectAsState().value
    val productImages = viewModel.productImages.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getAccount(context)
    }

    val fields = listOf<FieldsInfoItem>(
        FieldsInfoItem("Thông tin cá nhân", "personal_info"),
//        FieldsInfoItem("Nhắn tin", "message"),
        FieldsInfoItem("Thay đổi mật khẩu", "change_password"),
        FieldsInfoItem("Lịch sử mua hàng", "order_history",),
    )

    val orderStatus = listOf(
        OrderStatusItem(
            R.drawable.square_check_solid, DONE_GREEN,
            "Đã hoàn thành",
            orders.count { it.status.statusName.toString() == "Đã hoàn thành" },
            ""
        ),
        OrderStatusItem(
            R.drawable.square_check_solid, WAITING_YELLOW,
            "Đang xử lý",
            orders.count { it.status.statusName.toString() == "Đang xử lý" },
            ""
        ),
        OrderStatusItem(
            R.drawable.square_check_solid, DELIVERING_BLUE,
            "Đang vận chuyển",
            orders.count { it.status.statusName.toString() == "Đang vận chuyển" },
            ""
        ),
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                viewModel.updateImage(it, context)
            }
        }
    }

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
            AsyncImage(
                model = RetrofitClient.BASE_URL+"api/public/image?name=${customer.image?.name ?: "user.png"}",
                contentScale = ContentScale.Crop,
                contentDescription = "Avatar",
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .width(60.dp)
                    .height(60.dp)
                    .border(1.dp, BlackAlpha10, shape = CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Text(
                    text = "${customer.name}",
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${orders.count { it.status.statusName.toString() == "Đang vận chuyển" }} đơn đang giao",
                    color = GreenPrimary
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            FieldsInformation(fields, navController)
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        coroutineScope.launch { viewModel.logout(context, navController) }
                    }
            ) {
                Text(
                    text = "Đăng xuất",
                    modifier = Modifier.padding(Dimens.paddingMedium),
                )
            }
            OrderStatusOverview(orderStatus)
        }

        NavigationBar(navController)
    }
}