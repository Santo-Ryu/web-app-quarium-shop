package com.example.aquariumshopapp.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.ui.utils.ValidateUtils

@Composable
fun CommentReview(comment: Comment) {
    val customerImage = comment.customer.image?.name ?: "user.png"
    val customer = comment.customer
    Row(
        modifier = Modifier
            .padding(Dimens.paddingSmall)
    ) {
        AsyncImage(
            model = "${RetrofitClient.BASE_URL}api/public/image?name=${customerImage}",
            contentScale = ContentScale.Crop,
            contentDescription = "Image",
            modifier = Modifier
                .size(45.dp)
                .clip(shape = CircleShape)
                .border(1.dp, BlackAlpha10, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(Dimens.spaceSmall))
        Column() {
            Text(
                text = customer.name.toString(),
                style = Typography.titleMedium
            )
            Row {
                Text(
                    "${comment.rating}",
                    style = Typography.bodySmall
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    ", ${ValidateUtils.formatDate(comment.createdAt)}",
                    style = Typography.bodySmall
                )
            }
        }
    }
    Text(
        text = comment.content.toString(),
        modifier = Modifier.padding(Dimens.paddingSmall)
    )
    Divider(modifier = Modifier.fillMaxWidth())
}