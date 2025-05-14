package com.example.aquariumshopapp.ui.screens.search

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.home.components.NavigationBar
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.ui.screens.components.LabelList
import com.example.aquariumshopapp.ui.screens.components.ProductCardList
import com.example.aquariumshopapp.ui.screens.search.components.HeaderSearchBar
import com.example.aquariumshopapp.ui.theme.productCardModifier
import kotlin.toString

@Composable
fun SearchResultScreen(navController: NavController, input: String, type: String, viewModel: SearchViewModel = viewModel()) {
    val isLoading = viewModel.isDataLoaded.collectAsState().value
    val products = viewModel.products.collectAsState().value
    val productImageAll = viewModel.productImageAll.collectAsState().value
    val categories = viewModel.categories.collectAsState().value
    val context = LocalContext.current
    var selectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) { viewModel.getAccount(context) }

    val filteredProducts = if (type == "search") {
        products.filter {
            it.name?.contains(input, ignoreCase = true) == true
        }
    }else {
        emptyList()
    }

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderSearchBar(navController, input)

            Column(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        "Sắp xếp theo",
                        Modifier.padding(start = Dimens.paddingXSmall),
                        style = Typography.titleSmall
                    )

                    Spacer(Modifier.height(Dimens.spaceSmall))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .horizontalScroll(rememberScrollState())
                    ) {
                        categories.forEachIndexed { index, category ->
                            val isSelected = index == selectedIndex

                            val animatedBoxColor by animateColorAsState(
                                targetValue = if (isSelected) GreenPrimary else BlackAlpha10,
                            )
                            val animatedIconColor by animateColorAsState(
                                targetValue = if (isSelected) Color.White else Color.Black,
                            )
                            val interactionSource = remember { MutableInteractionSource() }
                            val scale by animateFloatAsState(
                                targetValue = if (isSelected) 1.1f else 1f,
                                animationSpec = tween(durationMillis = 300)
                            )

                            Spacer(modifier = Modifier.width(Dimens.paddingXSmall))

                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(Dimens.borderRadiusMedium))
                                    .background(animatedBoxColor)
                                    .padding(Dimens.paddingXSmall)
                                    .clickable(
                                        indication = ripple(bounded = true),
                                        interactionSource = interactionSource
                                    ){ selectedIndex = index }
                                ,
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    category.category.toString(),
                                    textAlign = TextAlign.Center,
                                    style = Typography.titleSmall,
                                    color = animatedIconColor,
                                    modifier = Modifier
                                        .graphicsLayer(scaleX = scale, scaleY = scale)
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    ProductCardList(
                        productImages = productImageAll,
                        cardModifier =  Modifier
                            .productCardModifier()
                            .then(Modifier.weight(1f)),
                        navController = navController,
                        products = filteredProducts
                    )
                }
            }

            NavigationBar(navController)
        }
    }

}