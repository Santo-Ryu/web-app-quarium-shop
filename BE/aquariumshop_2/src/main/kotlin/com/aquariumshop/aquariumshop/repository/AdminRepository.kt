package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.Admin
import com.aquariumshop.aquariumshop.model.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository: JpaRepository<Admin, Long> {
    fun findByEmail(email: String): Admin?
}