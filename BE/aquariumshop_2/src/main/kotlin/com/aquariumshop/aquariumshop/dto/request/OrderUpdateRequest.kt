package com.aquariumshop.aquariumshop.dto.request

import com.aquariumshop.aquariumshop.dto.response.OrderItemResponse
import com.aquariumshop.aquariumshop.model.entity.Product

data class OrderUpdateRequest(
    val orderId: Long,
    val orderStatus: String,
    val products: List<OrderItemUpdate>
)