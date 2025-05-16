package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.Order
import com.aquariumshop.aquariumshop.model.entity.OrderItem
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository: JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :id")
    fun findByOrderId(@Param("id") id: Long): List<OrderItem>

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM order_items WHERE order_id = :orderId AND product_id = :productId", nativeQuery = true)
    fun deleteByProductIdAndOrderId(@Param("orderId") orderId: Long, @Param("productId") productId: Long): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.order.id = :orderId")
    fun deleteByOrderId(@Param("orderId") orderId: Long)
}