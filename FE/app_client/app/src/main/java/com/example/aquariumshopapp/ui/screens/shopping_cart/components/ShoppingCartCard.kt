package com.example.aquariumshopapp.ui.screens.shopping_cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.R
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.CartItem
import com.example.aquariumshopapp.ui.utils.ValidateUtils

@Composable
fun ShoppingCartCard(
    cartItem: CartItem,
    increaseQuantity: () -> Unit,
    decreaseQuantity: () -> Unit,
) {
    val price = ValidateUtils.formatPrice((cartItem.price * (1 - cartItem.discountPercentage/100)).toString())

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp, top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = RetrofitClient.BASE_URL + "api/public/image?name=${cartItem.image}",
            contentDescription = "Product",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, BlackAlpha10)
        )
        Column(
            modifier = Modifier.height(100.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = cartItem.name,
                    style = Typography.titleMedium
                )
                Text(
                    text = price,
                    style = Typography.bodyLarge
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.clickable { decreaseQuantity() }
                ) { Icon(imageVector = Icons.Default.Remove, contentDescription = "Remove") }
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .border(1.dp, BlackAlpha30, shape = RoundedCornerShape(5.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = cartItem.quantity.toString())
                }
                Box(
                    modifier = Modifier.clickable { increaseQuantity() }
                ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add") }
            }
        }
    }
}