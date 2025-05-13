package com.example.aquariumshopapp.data.model

import java.time.LocalDateTime

data class OrderStatus(
    val id: Long,
    val statusName: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)