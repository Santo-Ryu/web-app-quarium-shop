package com.example.aquariumshopapp.ui.screens.product_details.components

import android.util.Log
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.theme.BUTTON_PRIMARY_GREEN
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.RATING_YELLOW_2
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.Customer
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.ui.utils.ValidateUtils

@Composable
fun ProductComment(
    navController: NavController,
    comments: List<Comment>,
    product: Product
) {
    val productComments = comments.filter { it.product?.id == product.id }
    Log.e("PRODUCT_COMMENT", product.toString())
    Log.e("PRODUCT_COMMENT", productComments.toString())

    if (productComments.isNullOrEmpty()) {
        return Text(
            text = "Chưa có đánh giá nào!",
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
        )
    }

    val paddingSet = Modifier
        .padding(
            start = Dimens.paddingMedium,
            end = Dimens.paddingMedium,
            top = Dimens.paddingXSmall,
            bottom = Dimens.paddingXSmall
        )

    val ratingsCount = (1..5).associateWith { star ->
        productComments.count { it.rating == star }
    }

    val totalReviews = productComments.size

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.then(paddingSet)
    ) {
        Text(
            "Đánh giá (${product.rating})",
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
                val count = ratingsCount[star] ?: 0
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
                            tint = RATING_YELLOW_2,
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            productComments.forEach { item ->
                val avatar = item.customer?.image?.name ?: "user.png"
                Column(
                    modifier = Modifier
                        .padding(
                            top = Dimens.paddingMedium,
                            bottom = Dimens.paddingSmall
                        )
                ) {
                    Row() {
                        AsyncImage(
                            model = "${RetrofitClient.BASE_URL}api/public/image?name=${avatar}",
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
                                item.customer?.name!!,
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
                                    ", ${ValidateUtils.formatDate(item.createdAt!!)}",
                                    style = Typography.bodySmall
                                )
                            }
                        }
                    }
                    Text(
                        text = item.content.toString(),
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
                onClick = { navController.navigate("product_review/${product.id}") },
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



