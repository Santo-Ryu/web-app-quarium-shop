package com.example.aquariumshopapp.data.model

data class CartItem(
    val productId: Long = 0,
    val image: String,
    val name: String,
    val quantity: Int,
    val price: Int,
    val discountPercentage: Int
)