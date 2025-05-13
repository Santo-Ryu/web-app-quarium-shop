package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.Category
import com.aquariumshop.aquariumshop.model.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByProductId(id: Long): List<Comment>
}