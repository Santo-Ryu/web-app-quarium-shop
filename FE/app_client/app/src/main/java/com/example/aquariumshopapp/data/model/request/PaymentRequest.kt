package com.example.aquariumshopapp.data.model.request

import com.example.aquariumshopapp.data.model.CartItem

data class PaymentRequest(
    val carts: List<CartItem>,
    val paymentMethod: String,
    val id: Long,
    val note: String ? = null
)
