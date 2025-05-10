package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository: JpaRepository<OrderItem, Long> {
}