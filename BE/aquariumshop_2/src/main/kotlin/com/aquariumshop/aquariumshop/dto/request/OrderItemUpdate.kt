package com.aquariumshop.aquariumshop.dto.request

data class OrderItemUpdate (
    val productId: Long,
    val productName: String,
    val image: String,
    val quantity: Int = 0,
    val price: Int = 0,
    val discount: Int = 0,
    val totalPrice: Int = 0
)