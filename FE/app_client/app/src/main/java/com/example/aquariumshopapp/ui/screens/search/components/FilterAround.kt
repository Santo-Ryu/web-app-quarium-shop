package com.example.aquariumshopapp.ui.screens.search.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.BlackAlpha30
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.ui.model.Label

@Composable
fun FilterAround(
    title: String,
    minPlaceholder: String,
    maxPlaceholder: String,
    labelList: List<Label>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 8.dp)
    ) {
        Text(
            text = title,
            style = Typography.titleSmall
        )
        Spacer(Modifier.height(Dimens.spaceSmall))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(minPlaceholder) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GreenPrimary
                ),
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "đến",
                style = Typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(.3f)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(maxPlaceholder) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GreenPrimary
                ),
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(Dimens.spaceSmall))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            var selectedIndex by remember { mutableStateOf(0) }
            labelList.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex

                val animatedBoxColor by animateColorAsState(
                    targetValue = if (isSelected) GreenPrimary else BlackAlpha10,
                )
                val animatedLabelColor by animateColorAsState(
                    targetValue = if (isSelected) White else BlackAlpha30,
                )
                val interactionSource = remember { MutableInteractionSource() }
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.1f else 1f,
                    animationSpec = tween(durationMillis = 300)
                )

                Box(
                    modifier = Modifier.weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(animatedBoxColor, shape = RoundedCornerShape(12.dp))
                        .height(45.dp)
                        .clickable(
                            indication = ripple(bounded = true),
                            interactionSource = interactionSource
                        ) { selectedIndex = index },
                    contentAlignment = Alignment.Center,
                ) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = item.content,
                            style = Typography.titleSmall,
                            color = animatedLabelColor,
                            modifier = Modifier
                                .graphicsLayer(scaleX = scale, scaleY = scale)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        item.imageVector?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = "Star",
                                modifier = Modifier.size(16.dp)
                                    .graphicsLayer(scaleX = scale, scaleY = scale),
                                tint = animatedLabelColor
                            )
                        }
                    }
                }
            }
        }
    }
}