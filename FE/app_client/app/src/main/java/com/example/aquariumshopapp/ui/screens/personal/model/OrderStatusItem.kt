package com.example.aquariumshopapp.ui.screens.personal.model

import androidx.compose.ui.graphics.Color

data class OrderStatusItem(
    val iconId: Int,
    val color: Color,
    val title: String,
    val totalOrder: Int,
    val navigate: String
)
