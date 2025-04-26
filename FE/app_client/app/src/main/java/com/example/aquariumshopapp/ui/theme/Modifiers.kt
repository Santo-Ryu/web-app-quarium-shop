package com.example.aquariumshopapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquarium_app.ui.theme.Dimens

fun Modifier.modifierOutlinedTextFieldAuthForm(): Modifier {
    return this
        .fillMaxWidth(.8f)
        .background(BlackAlpha10, shape = RoundedCornerShape(30.dp))
        .padding(Dimens.paddingSmall, 0.dp)
}