package com.example.aquariumshopapp.ui.screens.product_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.BUTTON_PRIMARY_GREEN
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R

@Composable
fun ProductComment() {
    val paddingSet = Modifier
        .padding(
            start = Dimens.paddingMedium,
            end = Dimens.paddingMedium,
            top = Dimens.paddingXSmall,
            bottom = Dimens.paddingXSmall
        )

//    Số lượt đánh giá theo rating
    val ratings = mapOf(
        5 to 40,
        4 to 20,
        3 to 10,
        2 to 5,
        1 to 20
    )
    val totalReviews = ratings.values.sum()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.then(paddingSet)
    ) {
        Text(
            "Đánh giá (139 lượt)",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.paddingXSmall),
            style = Typography.titleMedium,
            textAlign = TextAlign.Left
        )

        /*  Rating Chart  */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimens.borderRadiusSmall))
                .background(BlackAlpha10)
                .padding(Dimens.paddingMedium)
        ) {
            (5 downTo 1).forEach { star ->
                val count = ratings[star] ?: 0
                val progress = if (totalReviews == 0) 0f else count / totalReviews.toFloat()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(60.dp)
                    ) {
                        Text(
                            "$star",
                            style = Typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                    }


                    // Line Chart
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(10.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(BlackAlpha10)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress)
                                .height(10.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(GreenPrimary)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        if (count >=10) "$count lượt" else "0$count lượt",
                        style = Typography.bodySmall,
                        modifier = Modifier.width(50.dp)
                    )
                }
            }
        }

        /*  Comments  */
//        Giới hạn comment không quá 200 chữ
        data class Comment(
            val image: Int,
            val name: String,
            val rating: Int,
            val comment: String,
            val time: String
        )
        val comments = listOf(
            Comment(R.drawable.cay_dong_tien, "Santo", 4, "Rất tốt", "00:00:00"),
            Comment(R.drawable.cay_dong_tien, "Santo", 4, "Rất tốt", "00:00:00"),
            Comment(R.drawable.cay_dong_tien, "Santo", 4, "Rất tốt", "00:00:00"),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            comments.forEach { item ->
                Column(
                    modifier = Modifier
                        .padding(
                            top = Dimens.paddingMedium,
                            bottom = Dimens.paddingSmall
                        )
                ) {
                    Row() {
                        Image(
                            painter = painterResource(item.image),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(45.dp)
                                .clip(shape = CircleShape)
                                .border(1.dp, BlackAlpha10, shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.width(Dimens.spaceSmall))
                        Column() {
                            Text(
                                item.name,
                                style = Typography.titleMedium
                            )
                            Row {
                                Text(
                                    "${item.rating}",
                                    style = Typography.bodySmall
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFC107),
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    ", ${item.time}",
                                    style = Typography.bodySmall
                                )
                            }
                        }
                    }
                    Text(
                        item.comment,
                        modifier = Modifier
                            .padding(
                                top = Dimens.paddingSmall,
                                bottom = Dimens.paddingSmall,
                            )
                    )
                    Divider(modifier = Modifier.fillMaxWidth())
                }
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BUTTON_PRIMARY_GREEN
                )
            ) {
                Text(
                    "Tất cả đánh giá",
                    style = Typography.titleMedium,
                    color = White
                )
            }
        }
    }
}



