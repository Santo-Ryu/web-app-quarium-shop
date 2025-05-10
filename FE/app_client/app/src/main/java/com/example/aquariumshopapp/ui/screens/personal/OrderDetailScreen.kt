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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BackgroundColor
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.ui.screens.payment.components.PayProductCard

@Composable
fun OrderDetailScreen(navController: NavController) {

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
                "Thanh toán",
                style = TextStyle(
                    fontSize = 20.sp,
                )
            )
        }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(
                text = "Hoàn thành",
                modifier = Modifier.fillMaxWidth()
                    .background(GreenPrimary)
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
                    text = "Sản phẩm (2)",
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
                    repeat(10) {
                        PayProductCard(navController)
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
                        .fillMaxWidth()
                        .clickable { navController.navigate("address") },
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
                    // list foreach ra
                    Text("Santo Le", color = Color.Black.copy(.6f))
                    Text("(+84) 354 337 115", color = Color.Black.copy(.6f))
                    Text("04 Đ.Trần Hưng Đạo", color = Color.Black.copy(.6f))
                    Text("Phường Hòa Quý, Quận Ngũ Hành Sơn", color = Color.Black.copy(.6f))
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Lời nhắn:", // hiển thị số km
                    style = Typography.titleMedium
                )
                Text(
                    text = "lkajslkj",
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
                    text = "Thanh toán:", // hiển thị số km
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
                    Text("Tổng sản phẩm: 200,000 đ", color = Color.Black.copy(.6f))
                    Text("Phí vận chuyển: 20,000 đ", color = Color.Black.copy(.6f))
                    Text("Giảm giá: 200,000 đ", color = Color.Black.copy(.6f))
                    Text("Tổng phải trả: 220,000 đ", color = Color.Black.copy(.6f))
                    Text("Đã thanh toán", color = GreenPrimary, style = Typography.titleMedium)
                }
            }
        }
    }
}