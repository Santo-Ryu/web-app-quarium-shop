package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.OrderItemResponse
import com.aquariumshop.aquariumshop.model.entity.OrderItem
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [OrderMapper::class, ProductMapper::class])
interface OrderItemMapper {
    fun toResponse(orderItem: OrderItem): OrderItemResponse
    fun toEntity(response: OrderItemResponse): OrderItem
    fun toResponseList(orderItem: List<OrderItem>): List<OrderItemResponse>
    fun toEntityList(responses: List<OrderItemResponse>): List<OrderItem>
}