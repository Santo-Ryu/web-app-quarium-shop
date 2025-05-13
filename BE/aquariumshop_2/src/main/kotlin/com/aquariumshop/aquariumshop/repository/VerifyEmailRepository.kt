package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.VerifyEmail
import jakarta.transaction.Transactional
import jakarta.validation.constraints.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface VerifyEmailRepository: JpaRepository<VerifyEmail, Long>{
    fun findByEmail(email: String): VerifyEmail?
    fun findByToken(token: String): VerifyEmail?

    @Transactional
    @Modifying
    fun deleteByEmail(email: String): Long
}