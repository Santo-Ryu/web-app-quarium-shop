package com.aquariumshop.aquariumshop.dto.request

data class CartItemRequest(
    val productId: Long = 0,
    val image: String,
    val name: String,
    val quantity: Int,
    val price: Int,
    val discountPercentage: Int
)