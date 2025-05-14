package com.example.aquariumshopapp.data.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    val id: Long,
    val customer: Customer,
    val note: String?=null,
    val paymentMethod: String,
    val price: Int? = 0,
    val orderDate: String? = null,
    val status: OrderStatus,
    val createdAt: String
)
