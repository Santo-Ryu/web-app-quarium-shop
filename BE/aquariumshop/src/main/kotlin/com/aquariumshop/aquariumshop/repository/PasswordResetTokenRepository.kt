package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.PasswordResetToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PasswordResetTokenRepository: JpaRepository<PasswordResetToken, Long> {
}