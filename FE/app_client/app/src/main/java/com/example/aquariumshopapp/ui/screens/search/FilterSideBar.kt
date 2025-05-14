package com.example.aquariumshopapp.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.*
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.ui.model.Label
import com.example.aquariumshopapp.ui.screens.search.components.LabelList
import com.example.aquariumshopapp.ui.screens.search.components.FilterAround

@Composable
fun FilterSideBar(navController: NavController, input: String, type: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, BlackAlpha30)
            .background(White)
    ) {
        Row(
            modifier = Modifier
                .height(80.dp)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.arrow_left_solid),
                contentDescription = "Back to search result",
                modifier = Modifier.size(25.dp)
                    .clickable { navController.navigate("search_result/${input}") }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "Lọc sản phẩm",
                style = TextStyle(
                    fontSize = 20.sp,
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val categories = listOf(
                "Tất cả", "Cá cảnh", "Cây thủy sinh", "Dụng cụ", "Bể cá", "Thức ăn"
            )
            LabelList("Danh mục", categories)

            val reviewsOp = listOf(
                Label("1 - 1.9", Icons.Default.Star, null),
                Label("2 - 3.9", Icons.Default.Star, null),
                Label("4 - 5", Icons.Default.Star, null),
            )
            FilterAround("Đánh giá", "Từ 1.0 sao", "5.0 sao", reviewsOp)

            val priceAroundOp = listOf(
                Label("0 - 100k", null, null),
                Label("100k - 200k", null, null),
                Label("> 300k", null, null),
            )
            FilterAround("Khoảng giá (đ)", "Tối thiểu (0đ)", "Tối đa(1,000 đ)", priceAroundOp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val buttonList = listOf("Đặt lại", "Áp dụng")
            buttonList.forEachIndexed { index, item ->
                Button(
                    onClick = { navController.navigate("search_result") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (index == 0) BackgroundColor else GreenPrimary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = item,
                        style = Typography.titleMedium,
                        color = if (index == 0) Color.Black else White
                    )
                }
            }
        }
    }
}