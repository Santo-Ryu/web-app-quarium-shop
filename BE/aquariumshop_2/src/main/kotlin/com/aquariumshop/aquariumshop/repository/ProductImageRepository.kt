package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.ProductImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductImageRepository: JpaRepository<ProductImage, Long> {
    fun findByProductId(id: Long): List<ProductImage>
}