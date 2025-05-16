package com.example.aquariumshopapp.data.model

data class Comment(
    val id: Long = 0,
    val customer: Customer? = null,
    val product: Product? = null,
    val content: String? = null,
    val rating: Int? = 0,
    val createdAt: String? = null,
    val updatedAt: String? = null
)