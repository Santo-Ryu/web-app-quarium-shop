package com.aquariumshop.aquariumshop.dto.request

import com.aquariumshop.aquariumshop.enums.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthenticateRequest (
    @field:NotBlank(message = "Email không được để trống!")
    val email: String,

    val password: String? = null,

    @field:NotBlank(message = "Vai trò không hợp lệ!")
    val role: String
)