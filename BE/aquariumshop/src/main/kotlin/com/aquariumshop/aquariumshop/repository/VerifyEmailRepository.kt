package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.VerifyEmail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VerifyEmailRepository: JpaRepository<VerifyEmail, Long>{
}