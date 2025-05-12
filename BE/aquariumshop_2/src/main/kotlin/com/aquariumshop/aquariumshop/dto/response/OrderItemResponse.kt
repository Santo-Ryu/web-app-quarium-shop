package com.aquariumshop.aquariumshop.dto.response

import java.time.LocalDateTime

data class OrderItemResponse(
    val id: Long,
    val order: OrderResponse,
    val product: ProductResponse,
    val quantity: Int = 0,
    val price: Int = 0,
    val discountPercent: Int = 0,
    val subtotal: Int =0,
    val createdAt: String,
    val updatedAt: String
)
