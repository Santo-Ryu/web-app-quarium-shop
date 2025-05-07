package com.example.aquariumshopapp.ui.screens.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BackgroundColor
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R

@Composable
fun AddressScreen(navController: NavController) {
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
                contentDescription = "Back to pay",
                modifier = Modifier.size(30.dp)
                    .padding(start = 12.dp)
                    .clickable { navController.navigate("payment") }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "Địa chỉ",
                style = TextStyle(
                    fontSize = 20.sp,
                )
            )
        }
        Divider()

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(15) { Row(modifier = Modifier.height(95.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(White)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // list foreach ra
                    Text("Santo Le", style = Typography.titleMedium)
                    Text("(+84) 354 337 115", color = Color.Black.copy(.6f))
                    Text("04 Đ.Trần Hưng Đạo", color = Color.Black.copy(.6f))
                    Text("Phường Hòa Quý, Quận Ngũ Hành Sơn", color = Color.Black.copy(.6f))
                }
                Box(modifier = Modifier.width(20.dp).fillMaxHeight().background(GreenPrimary))
            } }
            Box(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                contentAlignment = Alignment.Center
            ) { Text("Hết", style = Typography.titleMedium) }
        }
    }
}