package com.aquariumshop.aquariumshop.dto.response

data class VerifyEmailResponse(
    val name: String,
    val token: String,
    val email: String,
    val purpose: String,
    val role: String
)
