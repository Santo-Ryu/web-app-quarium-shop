package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.category.category = :categoryName")
    fun findByCategory(categoryName: String): List<Product>
}