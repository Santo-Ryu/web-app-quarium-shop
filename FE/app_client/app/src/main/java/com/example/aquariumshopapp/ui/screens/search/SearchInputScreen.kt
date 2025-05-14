package com.example.aquariumshopapp.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.screens.home.components.NavigationBar
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.ui.utils.ValidateUtils

@Composable
fun SearchInputScreen(navController: NavController, viewModel: SearchViewModel = viewModel()) {
    val context = LocalContext.current
    val isLoading = viewModel.isDataLoaded.collectAsState().value
    val products = viewModel.products.collectAsState().value
    val productImages = viewModel.productImageAll.collectAsState().value
    var search_input by remember { mutableStateOf("") }


    val filteredProducts = if (search_input.isBlank()) {
        emptyList()
    } else {
        products.filter {
            it.name?.contains(search_input, ignoreCase = true) == true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAccount(context)
    }

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = search_input,
                    onValueChange = { search_input = it },
                    placeholder = { Text("Tìm kiếm...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = GreenPrimary,
                        unfocusedBorderColor = BlackAlpha30,
                        backgroundColor = White
                    ),
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 30.dp,
                            bottom = 12.dp,
                            start = 12.dp,
                            end = 12.dp
                        ),
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Search
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onSearch = {
//                            navController.navigate("search_result/${search_input}/{search}")
//                        }
//                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(BackgroundColor)
                    .verticalScroll(rememberScrollState())
            ) {
                filteredProducts.forEach { product ->
                    val image = productImages.find { it.product?.id == product.id }?.name ?: "loading.jpg"

                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp)
                            .fillMaxWidth()
                            .clickable { navController.navigate("product_details/${product.id}") }
                    ) {
                        AsyncImage(
                            model = "${RetrofitClient.BASE_URL}api/public/image?name=${image}",
                            contentDescription = "Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .size(70.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${product.name}",
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                )
                            )
                            Text(
                                text = "${ValidateUtils.formatPrice(product.price.toString())}",
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                "${product.salesCount} lượt bán | ${product.rating} đánh giá",
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black.copy(.6f)
                                )
                            )
                        }
                    }
                }
            }

            NavigationBar(navController)
        }
    }
}