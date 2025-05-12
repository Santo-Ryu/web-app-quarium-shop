package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
}