package com.example.aquariumshopapp.data.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AccountCreateRequest (
    val email: String,
    val password: String,
    val role: String
)