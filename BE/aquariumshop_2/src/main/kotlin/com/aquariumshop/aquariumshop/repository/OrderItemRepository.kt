package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.Order
import com.aquariumshop.aquariumshop.model.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository: JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :id")
    fun findByOrderId(@Param("id") id: Long): List<OrderItem>
}