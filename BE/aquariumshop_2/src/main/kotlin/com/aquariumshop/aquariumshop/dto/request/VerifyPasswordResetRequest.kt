package com.aquariumshop.aquariumshop.dto.request

data class VerifyPasswordResetRequest(
    val email: String,
    val password: String,
    val token: String,
    val role: String
)
