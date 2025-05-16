package com.aquariumshop.aquariumshop.dto.request

import com.aquariumshop.aquariumshop.dto.response.CategoryResponse
import com.aquariumshop.aquariumshop.enums.ProductStatus

data class ProductUpdateRequest(
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
