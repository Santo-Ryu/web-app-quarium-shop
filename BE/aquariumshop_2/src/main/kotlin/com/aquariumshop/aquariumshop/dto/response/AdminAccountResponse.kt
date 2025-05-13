package com.aquariumshop.aquariumshop.dto.response

data class AdminAccountResponse(
    val admin: AdminResponse,
    val orders: List<OrderResponse> = emptyList(),
    val products: List<ProductResponse> = emptyList(),
    val categories: List<CategoryResponse> = emptyList()
)
