package com.example.aquariumshopapp.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.Typography

@Composable
fun CategoryCard(
    text: String,
    index: Int,
    scale: Float = 1f,
    animatedBoxColor: Color,
    animatedIconColor: Color,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(Dimens.borderRadiusMedium))
            .background(animatedBoxColor)
            .padding(Dimens.paddingXSmall)
            .clickable(
                indication = ripple(bounded = true),
                interactionSource = interactionSource
            ){ onSelected(index) }
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            textAlign = TextAlign.Center,
            style = Typography.titleSmall,
            color = animatedIconColor,
            modifier = Modifier
                .graphicsLayer(scaleX = scale, scaleY = scale)
        )
    }
}