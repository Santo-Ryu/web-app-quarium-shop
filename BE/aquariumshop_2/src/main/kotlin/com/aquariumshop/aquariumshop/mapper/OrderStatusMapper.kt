package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.OrderStatusResponse
import com.aquariumshop.aquariumshop.model.entity.OrderStatus
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface OrderStatusMapper {
    fun toResponse(orderStatus: OrderStatus): OrderStatusResponse
    fun toEntity(response: OrderStatusResponse): OrderStatus
    fun toResponseList(ordersStatus: List<OrderStatus>): List<OrderStatusResponse>
    fun toEntityList(responses: List<OrderStatusResponse>): List<OrderStatus>
}