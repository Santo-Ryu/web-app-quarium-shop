package com.aquariumshop.aquariumshop.dto.response

import com.aquariumshop.aquariumshop.enums.ProductStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductResponse(
    val id: Long,
    val name: String? = null,
    val category: CategoryResponse? = null,
    val description: String? = null,
    val price: Int? = null,
    val discountPercentage: Int? = null,
    val discountStartDate: String? = null,
    val discountEndDate: String? = null,
    val quantity: Int = 0,
    val rating: Float? = null,
    val salesCount: Int? = null,
    val active: ProductStatus? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)
