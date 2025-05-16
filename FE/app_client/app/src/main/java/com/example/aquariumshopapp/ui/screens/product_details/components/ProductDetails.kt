package com.example.aquariumshopapp.ui.screens.product_details.components

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun ProductDetails(
    navController: NavController,
    product: Product,
    comments: List<Comment>,
    images: List<ProductImage>,
    productRelated: List<Product>,
    productImageRelated: List<ProductImage>,
    onComment: (rating: Int, comment: String) -> Any
) {
    val mainImage = remember { mutableStateOf(images.first()) }
    var comment by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(3) }
    val coroutineScope = rememberCoroutineScope()

    /*  Product Details  */
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Bottom,
    ) {
        /*  Main Product Image  */
        Crossfade(targetState = mainImage.value) { imageRes ->
            AsyncImage(
                model = "${RetrofitClient.BASE_URL}api/public/image?name=${imageRes.name}",
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .border(1.dp, BlackAlpha10)
            )
        }

        /*  Content  */
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val visible = product.discountPercentage == 0

            /*  Sale Tags  */
            if (!visible) SaleTag(product)

            /*  Title & Price  */
            ProductTitleAndPrice(
                visible = !visible,
                product = product
            )

            /*  Product Content  */
            ProductContent(
                productImages = images,
                onImageClick = { clickedImage -> mainImage.value = clickedImage },
                product = product
            )

            /*  Product Comments  */
            ProductComment(
                navController = navController,
                comments = comments,
                product = product
            )

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Bình luận:",
                    style = Typography.titleMedium
                )

                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("Nội dung...") },
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
                )

                var sliderPosition by remember { mutableStateOf(rating.toFloat()) }

                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        rating = it.toInt() // Cập nhật giá trị Int thực sự
                    },
                    valueRange = 1f..5f,
                    steps = 3, // chia thành 4 bước => 1-2-3-4-5
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        thumbColor = GreenPrimary,          // nút tròn
                        activeTrackColor = GreenPrimary,    // đoạn đã kéo
                        inactiveTrackColor = GreenPrimary.copy(alpha = 0.3f) // đoạn chưa kéo
                    )
                )
                Text(text = "Đánh giá: $rating sao")
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val response = onComment(rating, comment)
                            if (response == true) {
                                comment = ""
                                rating = 3
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = GreenPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(text = "Bình luận", color = White, style = Typography.titleLarge)
                }

            }

            /*  Product Related  */
            ProductRelated(
                products = productRelated,
                productImages = productImageRelated,
                navController = navController
            )
        }
    }
}