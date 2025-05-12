package com.aquariumshop.aquariumshop.dto.request

import com.aquariumshop.aquariumshop.enums.EmailPurpose
import com.aquariumshop.aquariumshop.enums.Role

data class VerifyEmailRequest(
    val token: String,
    val email: String,
    val role: String
)
