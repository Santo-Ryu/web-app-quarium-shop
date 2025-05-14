package com.example.aquariumshopapp.data.model

import com.example.aquariumshopapp.data.enums.Gender
import com.example.aquariumshopapp.data.enums.VerifyEmail
import java.time.LocalDateTime

data class Customer(
    val id: Long = 0,
    val email: String? = null,
    val name: String? = null,
    val gender: Gender? = null,
    val birthDate: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val verifyEmail: VerifyEmail? = null,
    val verifyAt: String? = null,
    val image: UserImage? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)
