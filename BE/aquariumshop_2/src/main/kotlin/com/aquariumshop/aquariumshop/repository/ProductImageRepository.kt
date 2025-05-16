package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.Product
import com.aquariumshop.aquariumshop.model.entity.ProductImage
import jakarta.transaction.Transactional
import org.hibernate.annotations.Parameter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductImageRepository: JpaRepository<ProductImage, Long> {
    fun findByProductId(id: Long): List<ProductImage>

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM product_images WHERE product_id = :productId", nativeQuery = true)
    fun deleteByProductId(@Param("productId") productId: Long)
}