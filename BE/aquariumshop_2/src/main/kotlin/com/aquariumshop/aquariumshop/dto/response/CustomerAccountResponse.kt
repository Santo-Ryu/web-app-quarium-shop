package com.aquariumshop.aquariumshop.dto.response

import com.aquariumshop.aquariumshop.enums.Gender
import java.time.LocalDateTime
import java.util.Date

data class CustomerAccountResponse (
    val customer: CustomerResponse? = null,
    val orders: List<OrderResponse> = emptyList(),
    val products: List<ProductResponse> = emptyList(),
    val categories: List<CategoryResponse> = emptyList(),
    val productImages: List<ProductImageResponse> = emptyList()
)