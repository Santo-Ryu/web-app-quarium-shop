package com.example.aquariumshopapp.ui.screens.personal.model

import androidx.compose.ui.graphics.Color

data class OrderStatusItem(
    val iconId: Int? = 0,
    val color: Color? = null,
    val title: String? = null,
    val totalOrder: Int? = null,
    val navigate: String? = null
)
