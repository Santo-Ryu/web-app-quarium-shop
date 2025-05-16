package com.example.aquariumshopapp.data.model

import java.util.Date

data class NotificationItem(
    val userId: String,
    val orderId: String,
    val status: String,
    val title: String,
    val timestamp: Date?
)
