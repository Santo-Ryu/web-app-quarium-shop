package com.example.aquariumshopapp.ui.screens.product_review

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.*
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.ui.screens.product_details.components.CommentReview

@Composable
fun ProductReviewScreen(navController: NavController) {
    val comments = listOf(
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
        Comment(R.drawable.cay_dong_tien, "Santo", "Rất tốt", 4, "dd/MM/yyyy hh:MM:ss"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .border(1.dp, BlackAlpha10)
                .background(BackgroundColor)
                .padding(start = Dimens.paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.arrow_left_solid),
                contentDescription = "Icon",
                modifier = Modifier.size(Dimens.iconSizeMedium)
                    .clickable { navController.navigate("product_details") }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "Đánh giá",
                style = Typography.titleLarge
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            comments.forEach { index -> CommentReview(index) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(BlackAlpha10)
            ) {
                Text(
                    text = "Đã hết"
                )
            }
        }
    }
}