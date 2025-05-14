package com.aquariumshop.aquariumshop.dto.request

data class PaymentRequest(
    val carts: List<CartItemRequest>,
    val paymentMethod: String,
    val id: Long,
    val note: String ? = null
)
