package com.example.aquariumshopapp.ui.screens.personal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aquarium_app.ui.theme.BlackAlpha10
import com.example.aquariumshopapp.R

@Composable
fun MessageItemAdmin() {

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(R.drawable.avt2),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .background(BlackAlpha10, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                text = "Xin chàokasjaksjdalsđkláldjkjjj",

                )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "10:30",
                color = Color.Gray,
                textAlign = TextAlign.Right
            )
        }
    }
}