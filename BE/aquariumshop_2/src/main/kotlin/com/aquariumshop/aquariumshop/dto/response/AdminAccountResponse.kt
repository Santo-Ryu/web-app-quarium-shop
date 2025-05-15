package com.aquariumshop.aquariumshop.dto.response

import com.aquariumshop.aquariumshop.model.entity.Customer

data class AdminAccountResponse(
    val admin: AdminResponse? = null,
    val orders: List<OrderResponse> = emptyList(),
    val orderItems: List<OrderItemResponse> = emptyList(),
    val products: List<ProductResponse> = emptyList(),
    val categories: List<CategoryResponse> = emptyList(),
    val productImages: List<ProductImageResponse> = emptyList(),
    val customers: List<CustomerResponse> = emptyList()
)
