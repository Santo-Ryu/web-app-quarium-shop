package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {
    fun findByCategory(category: String): Category?
}