package com.example.aquariumshopapp.data.model

data class Comment(
    val id: Long,
    val customer: Customer,
    val product: Product,
    val content: String?,
    val rating: Int?,
    val createdAt: String,
    val updatedAt: String
)