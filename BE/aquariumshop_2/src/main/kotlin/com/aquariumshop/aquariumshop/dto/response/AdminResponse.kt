package com.aquariumshop.aquariumshop.dto.response

import com.aquariumshop.aquariumshop.enums.Gender
import com.aquariumshop.aquariumshop.enums.VerifyEmailType
import java.time.LocalDateTime

data class AdminResponse (
    val id: Long,
    val email: String? = null,
    val name: String? = null,
    val gender: Gender? = null,
    val birthDate: String? = null,
    val phone: String? = null,
    val verifyEmail: VerifyEmailType? = null,
    val verifyAt: LocalDateTime? = null,
    val image: UserImageResponse? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)