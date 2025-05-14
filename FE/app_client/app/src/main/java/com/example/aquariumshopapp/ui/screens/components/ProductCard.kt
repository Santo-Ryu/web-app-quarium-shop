package com.example.aquarium_app.ui.screens.home.components

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.*
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.datastore.AccountDataStore
import com.example.aquariumshopapp.data.model.CartItem
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage
import com.example.aquariumshopapp.data.service.FilterImageList
import com.example.aquariumshopapp.data.service.ShoppingCartService
import com.example.aquariumshopapp.ui.model.TestData
import com.example.aquariumshopapp.ui.screens.components.ProductCardViewModel
import com.example.aquariumshopapp.ui.utils.NotificationUtils
import com.example.aquariumshopapp.ui.utils.ValidateUtils
import kotlinx.coroutines.launch

@Composable
fun ProductCard(
    modifier: Modifier,
    navController: NavController,
    product: Product,
    productImages: List<ProductImage>,
    viewModel: ProductCardViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val accountDataStore = AccountDataStore(context)

    val images = FilterImageList.filterImagesByProductId(productImages, product.id)

    val price = product.price ?: 0
    val discountPercentage = product.discountPercentage ?: 0

    val returnPrice = if (discountPercentage > 0) {
        val discountedPrice = price - (price * discountPercentage)/100
        ValidateUtils.formatPrice(discountedPrice.toString())
    } else {
        ValidateUtils.formatPrice(price.toString())
    }

    Column(
        modifier = modifier
            .clickable {
                navController.navigate("product_details/${product.id}")
            }
    ) {
//        Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.65f)
                .shadow(
                    elevation = 5.dp, // thấp = bóng ngắn
                    ambientColor = Color.Black.copy(.3f), // đậm hơn
                    spotColor = Color.Black.copy(.3f)      // đậm hơn
                )
        ) {
            AsyncImage(
                model = "${RetrofitClient.BASE_URL}api/public/image?name=${images.first().name}",
                contentDescription = "Product",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(
                        bottomEnd = Dimens.borderRadiusSmall.dp
                    ))
                    .height(28.dp)
                    .background(
                        GreenPrimary,
                        shape = RoundedCornerShape(
                            bottomEnd = Dimens.borderRadiusSmall.dp
                        )
                    )
                    .padding(5.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.rating.toString(),
                    style = Typography.titleMedium,
                    color = RATING_YELLOW_2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
                Image(
                    painter = painterResource(R.drawable.star_solid),
                    contentDescription = "Product Image",
                    colorFilter = ColorFilter.tint(RATING_YELLOW_2),
                    modifier = Modifier
                        .size(Dimens.iconSizeSmall)
                )
            }
        }

//        Details & Price
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Title & product detail
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
//                Title
                Text(
                    text = product.name.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 6.dp,
                            start = 8.dp
                        ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = Typography.titleMedium
                )
//                Product detail
                Text(
                    text = product.description.toString(),
                    modifier = Modifier
                        .padding(
                            bottom = 6.dp,
                            start = 8.dp
                        )
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = detailTextSmall
                )
            }

//            Price and add to cart
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Price label
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
//                    If sale then display
                    if (product.discountPercentage != null && product.discountPercentage > 0) {
                        Text(
                            text = ValidateUtils.formatPrice(product.price.toString()),
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Red,
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                    }
                    Text(
                        text = returnPrice,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

//                Button add to cart
                var isPressed by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (isPressed) 0.9f else 1f,
                    animationSpec = tween(durationMillis = 100),
                    label = "ScaleAnimation"
                )
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(GreenPrimary)
                        .height(22.dp)
                        .width(40.dp)
                        .clickable(
                            indication = ripple(bounded = true),
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isPressed = !isPressed
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.plus_solid),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = "Add to cart",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .graphicsLayer(scaleX = scale, scaleY = scale)
                            .clickable {
                                coroutineScope.launch {
                                    viewModel.addToShoppingCart(
                                        product = product,
                                        userId = accountDataStore.getAccount()?.id!!.toString(),
                                        context = context,
                                        image = images.first().name
                                    )
                                }
                            }
                    )
                }
            }
        }
    }
}