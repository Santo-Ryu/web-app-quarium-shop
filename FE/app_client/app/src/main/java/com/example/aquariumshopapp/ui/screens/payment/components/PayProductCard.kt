package com.example.aquariumshopapp.ui.screens.payment.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.CartItem
import com.example.aquariumshopapp.data.model.OrderItem
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.ui.utils.ValidateUtils

@Composable
fun PayProductCard(
    item: CartItem,
    image: String,
    product: Product
) {
    val visible = item.discountPercentage != null && item.discountPercentage!! > 0
    val discountAmount = item.price * (item.discountPercentage.toDouble() / 100)
    val finalPrice = item.price - discountAmount

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = RetrofitClient.BASE_URL+"api/public/image?name=$image",
            contentDescription = "Product",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(70.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, BlackAlpha10, shape = RoundedCornerShape(12.dp))
        )
        Column() {
            Text(
                text = "${product.name}",
                style = Typography.titleMedium
            )
            Text(
                text = "${ValidateUtils.formatPrice(finalPrice.toString())}",
                style = Typography.bodyLarge
            )
            Text(
                text = "Số lượng: ${item.quantity}",
                style = Typography.bodySmall
            )
            if (visible) {
                Text(
                    text = "Giảm giá: ${item.discountPercentage}% (-${ValidateUtils.formatPrice(discountAmount.toString())})", // nếu có giảm giá thì display
                    style = Typography.bodySmall
                )
            }
        }
    }
}