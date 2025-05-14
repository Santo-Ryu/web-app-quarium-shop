package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.customer.id = :id")
    fun findByCustomerId(@Param("id") id: Long): List<Order>
}