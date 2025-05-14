package com.aquariumshop.aquariumshop.dto.response

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponse(
    val id: Long,
    val customer: CustomerResponse,
    val price: Int? = null,
    val orderDate: String? = null,
    val status: OrderStatusResponse
)