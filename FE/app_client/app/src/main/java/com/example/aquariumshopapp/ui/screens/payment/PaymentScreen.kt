package com.example.aquariumshopapp.ui.screens.payment

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BackgroundColor
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.enums.PaymentMethod
import com.example.aquariumshopapp.ui.screens.payment.components.PayProductCard
import com.example.aquariumshopapp.ui.utils.ValidateUtils
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(navController: NavController, viewModel: PaymentViewModel = viewModel()) {
    val isLDataLoaded = viewModel.isDataLoaded.collectAsState().value
    val customer = viewModel.customer.collectAsState().value
    val orders = viewModel.orders.collectAsState().value
    val productImages = viewModel.productImageAll.collectAsState().value
    val orderImages = viewModel.orderItems.collectAsState().value
    val carts = viewModel.carts.collectAsState().value
    val total = viewModel.total.collectAsState().value
    val context = LocalContext.current
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.COD) }
    val coroutineScope = rememberCoroutineScope()
    var note by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getAccount(context)
    }

    if (isLDataLoaded) {
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
                        .clickable { navController.navigate("home") }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Thanh toán",
                    style = TextStyle(
                        fontSize = 20.sp,
                    )
                )
            }

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                /*  List Product  */
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Sản phẩm (${carts.size})",
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
                        carts.forEach { item ->
                            val product = productImages.find { it.product?.id == item.productId }?.product
                            Log.e("PAYMENT_LOG", "$productImages")
                            Log.e("PAYMENT_LOG", "$product")
                            Log.e("PAYMENT_LOG", "$item")
                            Log.e("PAYMENT_LOG", "${item.productId}, ${item.name}, ${item.image}, ${item.price}, ${item.discountPercentage}, ${item.quantity}, ")
                            if (product != null) {
                                PayProductCard(
                                    item = item,
                                    image = item.image,
                                    product = product
                                )
                            }
                        }
                    }
                }

                /*  Choose Address  */
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
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BackgroundColor, shape = RoundedCornerShape(12.dp))
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // list foreach ra
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

                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        placeholder = { Text("Nội dung...") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = GreenPrimary,
                            unfocusedBorderColor = BlackAlpha30,
                            backgroundColor = White
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
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
                        // list foreach ra
                        Text("Phí vận chuyển: miễn phí đ", color = Color.Black.copy(.6f))
                        Text("Tổng phải thanh toán: ${ValidateUtils.formatPrice(total.toString())}", color = Color.Black.copy(.6f))
                    }
                }

                /* Payment Method Selection */
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Phương thức thanh toán:",
                        style = Typography.titleMedium
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPaymentMethod = PaymentMethod.COD }
                            .padding(8.dp)
                            .background(
                                if (selectedPaymentMethod == PaymentMethod.COD) GreenPrimary.copy(alpha = 0.1f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        RadioButton(
                            selected = selectedPaymentMethod == PaymentMethod.COD,
                            onClick = { selectedPaymentMethod = PaymentMethod.COD }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Thanh toán khi nhận hàng")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPaymentMethod = PaymentMethod.QR }
                            .padding(8.dp)
                            .background(
                                if (selectedPaymentMethod == PaymentMethod.QR) GreenPrimary.copy(alpha = 0.1f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        RadioButton(
                            selected = selectedPaymentMethod == PaymentMethod.QR,
                            onClick = { selectedPaymentMethod = PaymentMethod.QR }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Thanh toán bằng QR")
                    }
                }

                /*  Button  */
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.payment(context, selectedPaymentMethod.toString(), note, navController)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = GreenPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .height(60.dp)
                        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "Thanh toán",
                        color = White,
                        style = Typography.titleLarge
                    )
                }
            }
        }
    }
}