package com.example.aquariumshopapp.ui.screens.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquariumshopapp.data.model.Category

@Composable
fun LabelList(
    list: List<Category>,
    navController: NavController
) {
    var selectedIndex by remember { mutableStateOf(0) }

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
            list.forEachIndexed { index, category ->
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
}