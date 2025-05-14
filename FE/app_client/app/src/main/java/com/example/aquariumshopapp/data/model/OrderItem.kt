package com.example.aquariumshopapp.data.model

data class OrderItem (
    val id: Long,
    val order: Order,
    val product: Product,
    val quantity: Int = 0,
    val price: Int = 0,
    val discountPercent: Int = 0,
    val subtotal: Int =0,
    val createdAt: String,
    val updatedAt: String
)