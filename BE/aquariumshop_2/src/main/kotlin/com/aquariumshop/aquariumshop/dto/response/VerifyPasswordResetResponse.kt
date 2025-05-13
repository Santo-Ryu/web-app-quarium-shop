package com.aquariumshop.aquariumshop.dto.response

data class VerifyPasswordResetResponse(
    val email: String,
    val password: String,
    val token: String,
    val role: String
)