package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.OrderResponse
import com.aquariumshop.aquariumshop.model.entity.Order
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [CustomerMapper::class, ProductMapper::class, OrderStatusMapper::class])
interface OrderMapper {
    fun toResponse(order: Order): OrderResponse
    fun toEntity(response: OrderResponse): Order
    fun toResponseList(orders: List<Order>): List<OrderResponse>
    fun toEntityList(responses: List<OrderResponse>): List<Order>
}