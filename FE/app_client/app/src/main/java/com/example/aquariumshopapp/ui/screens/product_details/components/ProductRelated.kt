package com.example.aquariumshopapp.ui.screens.product_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage
import com.example.aquariumshopapp.ui.screens.components.Slide

@Composable
fun ProductRelated(
    products: List<Product>,
    productImages: List<ProductImage>,
    navController: NavController
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
                painter = painterResource(R.drawable.leaf_solid),
                contentDescription = "Liên quan",
                colorFilter = ColorFilter.tint(GreenPrimary)
            )
            Spacer(modifier = Modifier.width(Dimens.paddingSmall))
            Text(
                text = "Liên quan",
                color = GreenPrimary,
                style = Typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(Dimens.paddingSmall))

        /*  Slide  */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxSize(),
            ) {
                products.forEach { item ->
                    val image = productImages.find { it.product?.id === item.id}?.name
                    Column(
                        modifier = Modifier
                            .width(140.dp)
                            .clip(RoundedCornerShape(Dimens.borderRadiusSmall.dp))
                            .border(
                                1.dp, BlackAlpha10,
                                RoundedCornerShape(Dimens.borderRadiusSmall.dp)
                            )
                            .clickable {
                                navController.navigate("product_details/${item.id}")
                            }
                    ) {
                        AsyncImage(
                            model = "${RetrofitClient.BASE_URL}api/public/image?name=${image}",
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
                                text = item.name.toString(),
                                color = Color.Black,
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