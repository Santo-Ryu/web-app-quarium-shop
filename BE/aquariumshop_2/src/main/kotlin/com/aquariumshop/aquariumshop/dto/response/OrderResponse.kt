package com.aquariumshop.aquariumshop.dto.response

import com.aquariumshop.aquariumshop.enums.PaymentMethod
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponse(
    val id: Long,
    val customer: CustomerResponse,
    val note: String? =null,
    val paymentMethod: String,
    val price: Int? = null,
    val orderDate: String? = null,
    val status: OrderStatusResponse,
    val createdAt: String
)