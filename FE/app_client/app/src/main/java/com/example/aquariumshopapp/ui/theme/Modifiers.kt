package com.example.aquariumshopapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens

fun Modifier.modifierOutlinedTextFieldAuthForm(): Modifier {
    return this
        .fillMaxWidth(.8f)
        .background(BlackAlpha10, shape = RoundedCornerShape(30.dp))
        .padding(Dimens.paddingSmall, 0.dp)
}

fun Modifier.productCardModifier(): Modifier {
    return this
        .clip(RoundedCornerShape(8.dp))
        .border(
            1.dp, BlackAlpha10,
            shape = RoundedCornerShape(8.dp)
        )
        .height(240.dp)
}