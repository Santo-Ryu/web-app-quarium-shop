package com.example.aquariumshopapp.ui.screens.personal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BackgroundColor
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.DELIVERING_BLUE
import com.example.aquarium_app.ui.theme.DONE_GREEN
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.WAITING_YELLOW
import com.example.aquarium_app.ui.theme.White
import com.example.aquarium_app.ui.theme.textButtonSmall
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.datastore.AccountDataStore
import com.example.aquariumshopapp.data.model.OrderItem
import com.example.aquariumshopapp.ui.screens.payment.components.PayProductCard
import com.example.aquariumshopapp.ui.screens.personal.PersonalViewModel
import com.example.aquariumshopapp.ui.screens.personal.components.OrderDetailsItem
import com.example.aquariumshopapp.ui.utils.ValidateUtils
import kotlinx.coroutines.launch

@Composable
fun OrderDetailScreen(navController: NavController, id: Long, viewModel: PersonalViewModel = viewModel()) {
    val context = LocalContext.current
    val isDataLoaded = viewModel.isDataLoaded.collectAsState().value
    val orders = viewModel.orders.collectAsState().value
    val orderItems = viewModel.orderItems.collectAsState().value
    val productImage = viewModel.productImages.collectAsState().value
    val customer = viewModel.customer.collectAsState().value

    LaunchedEffect(id) {
        viewModel.getAccount(context)
    }

    if (isDataLoaded) {
        val order = orders.find { it.id == id }
        val items: MutableList<OrderItem> = mutableListOf()

        var priceO = 0
        var discountO = 0

        orderItems.filter { it.order.id == id }.forEach { item ->
            priceO += item.price*item.quantity
            if (item.discountPercent != null && item.discountPercent > 0) {
                discountO += (item.price * (item.discountPercent.toDouble() / 100)).toInt()
            }
        }

        val iconColor = when(order?.status?.statusName) {
            "Đã hoàn thành" -> DONE_GREEN
            "Đang xử lý" -> WAITING_YELLOW
            else -> DELIVERING_BLUE
        }

        orderItems.forEach { item ->
            if (item.order.id == id) items.add(item)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor.copy(.3f))
        ) {
            /*  Header  */
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.arrow_left_solid),
                    contentDescription = "Back to home",
                    modifier = Modifier.size(30.dp)
                        .padding(start = 12.dp)
                        .clickable { navController.navigate("order_history") }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Đơn hàng",
                    style = TextStyle(
                        fontSize = 20.sp,
                    )
                )
            }

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = "${order?.status?.statusName}",
                    modifier = Modifier.fillMaxWidth()
                        .background(iconColor)
                        .padding(15.dp),
                    color = White,
                    style = Typography.titleMedium
                )

                /*  List Product  */
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Sản phẩm (${items.size})",
                        style = Typography.titleMedium
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, BlackAlpha10, shape = RoundedCornerShape(12.dp))
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 8.dp)
                    ) {
                        items.forEach { item ->
                            val image = productImage.find { it.product?.id == item.product.id }?.name ?: ""
                            OrderDetailsItem(
                                item = item,
                                image = image
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Địa chỉ",
                            style = Typography.titleMedium
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Choose Address",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BackgroundColor, shape = RoundedCornerShape(12.dp))
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("${customer.name}", color = Color.Black.copy(.6f))
                        Text("${customer.phone}", color = Color.Black.copy(.6f))
                        Text("${customer.address}", color = Color.Black.copy(.6f))
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Lời nhắn:",
                        style = Typography.titleMedium
                    )
                    Text(
                        text = "${order?.note}",
                        modifier = Modifier.fillMaxWidth()
                            .background(BackgroundColor, shape = RoundedCornerShape(12.dp))
                            .padding(12.dp)

                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Phương thức thanh toán:",
                        style = Typography.titleMedium
                    )
                    Text(
                        text = "${order?.paymentMethod}",
                        modifier = Modifier.fillMaxWidth()
                            .background(BackgroundColor, shape = RoundedCornerShape(12.dp))
                            .padding(12.dp)

                    )
                }

                /*  Total  */
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Thanh toán:",
                        style = Typography.titleMedium
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BackgroundColor, shape = RoundedCornerShape(12.dp))
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Tổng tiền sản phẩm: ${ValidateUtils.formatPrice(priceO.toString())}", color = Color.Black.copy(.6f))
                        Text("Phí vận chuyển: miễn phí", color = Color.Black.copy(.6f))
                        Text("Giảm giá: ${ValidateUtils.formatPrice(discountO.toString())}", color = Color.Black.copy(.6f))
                        Text("Tổng phải trả: ${ValidateUtils.formatPrice(order?.price.toString())}", color = Color.Black.copy(.6f))
                        Text("${order?.status?.statusName}", color = iconColor, style = Typography.titleMedium)
                    }
                }
            }
        }
    }
}