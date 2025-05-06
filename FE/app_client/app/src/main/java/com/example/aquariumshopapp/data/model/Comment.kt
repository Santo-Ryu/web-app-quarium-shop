package com.example.aquariumshopapp.data.model

import java.time.LocalDateTime

data class Comment(
    val image: Int,
    val name: String,
    val comment: String,
    val rating: Int,
    val createdAt: String
)
