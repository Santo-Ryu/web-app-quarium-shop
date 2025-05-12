package com.example.aquariumshopapp.data.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    val id: Long,
    val customer: Customer,
    val product: Product,
    val price: BigDecimal? = null,
    val orderDate: String? = null,
    val status: OrderStatus
)
