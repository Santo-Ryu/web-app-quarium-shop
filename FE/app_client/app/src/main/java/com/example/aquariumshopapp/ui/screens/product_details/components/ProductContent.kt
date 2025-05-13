package com.example.aquariumshopapp.ui.screens.product_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.PRODUCT_TEXT_DETAILS
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage

@Composable
fun ProductContent(
    productImages: List<ProductImage>,
    onImageClick: (ProductImage) -> Unit,
    product: Product
) {
    val paddingSet = Modifier
        .padding(
            start = Dimens.paddingMedium,
            end = Dimens.paddingMedium,
            top = Dimens.paddingXSmall
        )

    /*  List Images  */
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            "Hình ảnh",
            modifier = Modifier
                .then(paddingSet),
            style = Typography.titleMedium
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .then(paddingSet)
        ) {
            productImages.forEach { imageRes ->
                AsyncImage(
                    model = "${RetrofitClient.BASE_URL}api/public/image?name=${imageRes.name}",
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(Dimens.borderRadiusSmall))
                        .border(
                            1.dp, BlackAlpha10,
                            shape = RoundedCornerShape(Dimens.borderRadiusSmall)
                        )
                        .clickable { onImageClick(imageRes) }
                )
                Spacer(modifier = Modifier.width(Dimens.spaceMedium))
            }
        }
    }

    /*  Expand Details  */
    val isExpanded = remember { mutableStateOf(false) }
    val showMoreText = if (isExpanded.value) "Thu gọn" else "Xem thêm"
    val maxLines = if (isExpanded.value) Int.MAX_VALUE else 3

    Column(
        modifier = Modifier
    ) {
        Text(
            "Mô tả",
            modifier = Modifier.then(paddingSet),
            style = Typography.titleMedium
        )

        Text(
            text = product.description.toString(),
            modifier = Modifier
                .padding(
                    start = Dimens.paddingMedium,
                    end = Dimens.paddingMedium,
                    top = Dimens.paddingSmall
                ),
            style = Typography.bodyMedium,
            color = PRODUCT_TEXT_DETAILS,
            maxLines = maxLines
        )

        Text(
            text = showMoreText,
            modifier = Modifier
                .padding(horizontal = Dimens.paddingMedium, vertical = 4.dp)
                .clickable { isExpanded.value = !isExpanded.value },
            style = Typography.bodyMedium,
            textDecoration = TextDecoration.Underline,
            color = PRODUCT_TEXT_DETAILS
        )
    }
}