package com.aquariumshop.aquariumshop.dto.response

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val customer: CustomerResponse,
    val product: ProductResponse,
    val content: String?,
    val rating: Int?,
    val createdAt: String,
    val updatedAt: String
)
