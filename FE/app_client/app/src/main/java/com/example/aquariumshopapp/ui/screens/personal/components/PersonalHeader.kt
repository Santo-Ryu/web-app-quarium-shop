package com.example.aquariumshopapp.ui.screens.personal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aquarium_app.ui.theme.Dimens
import com.example.aquarium_app.ui.theme.GreenPrimary
import com.example.aquarium_app.ui.theme.Typography
import com.example.aquarium_app.ui.theme.White
import com.example.aquariumshopapp.R

@Composable
fun PersonalHeader(
    navController: NavController,
    title: String,
    route: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(70.dp)
            .background(GreenPrimary)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.angle_left_solid),
            contentDescription = "Back",
            colorFilter = ColorFilter.tint(White),
            modifier = Modifier.size(Dimens.iconSizeMedium)
                .clickable { navController.navigate(route) }
        )
        Text(
            text = title,
            style = Typography.titleMedium,
            color = White
        )
    }
}