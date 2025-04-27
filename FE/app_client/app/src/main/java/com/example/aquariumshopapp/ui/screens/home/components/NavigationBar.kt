package com.example.aquarium_app.ui.screens.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquariumshopapp.R

@Composable
fun NavigationBar(modifier: Modifier) {
    var selectedIndex by remember { mutableStateOf(0) }
    val listIcon = listOf(
        R.drawable.house_solid,
        R.drawable.cart_shopping_solid,
        R.drawable.bell_solid,
        R.drawable.user_solid
    )

    Row(
        modifier = modifier
            .padding(20.dp, 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(4) { index ->
            val isSelected = index == selectedIndex

            // Animate background and icon color
            val animatedBoxColor by animateColorAsState(
                targetValue = if (isSelected) GreenPrimary else Color.Transparent,
            )
            val animatedIconColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else GreenPrimary,
            )
            val interactionSource = remember { MutableInteractionSource() }
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = tween(durationMillis = 300)
            )

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .border(
                        1.dp, Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(animatedBoxColor)
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable(
                        indication = ripple(bounded = true),
                        interactionSource = interactionSource
                    ){ selectedIndex = index },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(listIcon[index]),
                    contentDescription = "Navigation Icon",
                    modifier = Modifier
                        .size(25.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale),
                    colorFilter = ColorFilter.tint(animatedIconColor)
                )
            }
        }
    }
}