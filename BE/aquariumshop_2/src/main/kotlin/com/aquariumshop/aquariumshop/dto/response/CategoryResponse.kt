package com.aquariumshop.aquariumshop.dto.response

data class CategoryResponse (
    val id: Long,
    val category: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)