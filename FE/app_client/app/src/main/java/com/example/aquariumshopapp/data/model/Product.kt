package com.example.aquariumshopapp.data.model

import com.example.aquariumshopapp.data.enums.ProductStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class Product(
    val id: Long = 0,
    val name: String? = null,
    val category: Category? = null,
    val description: String? = null,
    val price: Int? = null,
    val discountPercentage: Int? = null,
    val discountStartDate: String? = null,
    val discountEndDate: String? = null,
    val rating: Float? = null,
    val salesCount: Int? = null,
    val active: ProductStatus? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)
