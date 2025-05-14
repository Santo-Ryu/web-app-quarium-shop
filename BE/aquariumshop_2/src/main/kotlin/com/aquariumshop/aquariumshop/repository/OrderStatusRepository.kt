package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderStatusRepository: JpaRepository<OrderStatus, Long> {
    fun findByStatusName(statusName: String): OrderStatus
}