package com.example.aquariumshopapp.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.*
import com.example.aquariumshopapp.R

@Composable
fun Slide(
    iconId: Int,
    title: String,
    colorTemplate: Color
) {
    Column(
        modifier = Modifier
            .padding(Dimens.paddingXSmall)
    ) {
        /*  Title  */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(iconId),
                contentDescription = title,
                colorFilter = ColorFilter.tint(colorTemplate)
            )
            Spacer(modifier = Modifier.width(Dimens.paddingSmall))
            Text(
                text = title,
                color = colorTemplate,
                style = Typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(Dimens.paddingSmall))

        /*  Sale slide  */
        val products by remember{
            mutableStateOf(
                listOf(
                    R.drawable.cay_dong_tien,
                    R.drawable.cay_dong_tien,
                    R.drawable.cay_dong_tien,
                    R.drawable.cay_dong_tien,
                    R.drawable.banner2,
                )
            )
        }

        /*  Slide  */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(products.size) { index ->
                    Column(
                        modifier = Modifier
                            .width(140.dp)
                            .clip(RoundedCornerShape(Dimens.borderRadiusSmall.dp))
                            .border(
                                1.dp, BlackAlpha10,
                                RoundedCornerShape(Dimens.borderRadiusSmall.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(products[index]),
                            contentDescription = "Product",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxHeight(.8f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Cây Đồng Tiền Đẹp",
                                color = colorTemplate,
                                style = Typography.titleMedium,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth(.85f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(Dimens.paddingSmall))
                }
            }
        }
    }
}