package com.example.aquariumshopapp.data.model.request

import com.example.aquariumshopapp.data.enums.Role

data class AuthenticateRequest(
    val email: String,
    val password: String? = null,
    val role: String
)