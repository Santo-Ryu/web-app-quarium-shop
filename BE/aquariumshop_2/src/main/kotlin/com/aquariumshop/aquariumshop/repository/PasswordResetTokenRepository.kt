package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.PasswordResetToken
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface PasswordResetTokenRepository: JpaRepository<PasswordResetToken, Long> {
    fun findByToken(token: String): PasswordResetToken?
    fun findByEmail(token: String): PasswordResetToken?

    @Transactional
    @Modifying
    fun deleteByEmail(email: String): Long
}