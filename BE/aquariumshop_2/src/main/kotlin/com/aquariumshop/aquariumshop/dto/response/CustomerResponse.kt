package com.aquariumshop.aquariumshop.dto.response

import com.aquariumshop.aquariumshop.enums.Gender
import com.aquariumshop.aquariumshop.enums.VerifyEmailType
import com.aquariumshop.aquariumshop.model.entity.UserImage
import java.time.LocalDateTime

data class CustomerResponse (
    val id: Long,
    val email: String? = null,
    val name: String? = null,
    val gender: Gender? = null,
    val birthDate: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val verifyEmail: VerifyEmailType? = null,
    val verifyAt: String? = null,
    val image: UserImageResponse? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)