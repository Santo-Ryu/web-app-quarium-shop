package com.aquariumshop.aquariumshop.dto.response

import java.time.LocalDateTime

data class OrderStatusResponse(
    val id: Long,
    val statusName: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)
