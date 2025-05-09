package com.example.aquariumshopapp.ui.screens.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.home.components.ProductCard
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.*
import com.example.aquariumshopapp.R
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SaleOff(navController: NavController) {
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
                painter = painterResource(R.drawable.tags_solid),
                contentDescription = "Sale off",
                colorFilter = ColorFilter.tint(SALE_OFF_TAG)
            )
            Spacer(modifier = Modifier.width(Dimens.paddingSmall))
            Text(
                text = "SALE OFF",
                color = SALE_OFF_TAG,
                style = Typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(Dimens.paddingSmall))

        /*  Sale slide  */
        data class TestData(val id: Int, val price: String)
        val saleProducts by remember{
            mutableStateOf(
                listOf(
                    TestData(R.drawable.cay_dong_tien, "12,000"),
                    TestData(R.drawable.beta2, "120,000"),
                    TestData(R.drawable.beca, "89,000"),
                    TestData(R.drawable.cay_dong_tien, "12,000")
                )
            )
        }

        /*  Slide  */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp) 
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState()),
            ) {
                saleProducts.forEach { item ->
                    Column(
                        modifier = Modifier
                            .width(140.dp)
                            .clip(RoundedCornerShape(Dimens.borderRadiusSmall.dp))
                            .border(
                                1.dp, BlackAlpha10,
                                RoundedCornerShape(Dimens.borderRadiusSmall.dp)
                            )
                            .clickable { navController.navigate("product_details") }
                    ) {
                        Image(
                            painter = painterResource(item.id),
                            contentDescription = "Product",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxHeight(.8f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.price,
                                color = SALE_OFF_TAG,
                                style = Typography.titleMedium,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(Dimens.paddingSmall))
                }
            }
        }
    }
}