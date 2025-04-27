package com.example.aquarium_app.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aquarium_app.ui.theme.Dimens.paddingMedium
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquariumshopapp.R

@Composable
fun Header(modifier: Modifier) {
    Surface(
        modifier = modifier,
        color = Color.White,
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = paddingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(R.drawable.leaf_solid),
                contentDescription = "Logo",
                colorFilter = ColorFilter.tint(GreenPrimary),
                modifier = Modifier.size(28.dp)
            )

            Text(
                "AQUARIUM SHOP",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = GreenPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(.2f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                )
            )

            Image(
                painter = painterResource(R.drawable.magnifying_glass_solid),
                contentDescription = "Logo",
                colorFilter = ColorFilter.tint(GreenPrimary),
                modifier = Modifier.size(28.dp)
            )
        }
}
}