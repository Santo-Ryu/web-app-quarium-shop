package com.aquariumshop.aquariumshop.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AccountCreateRequest (
    @field:NotBlank(message = "Email không được để trống!")
    @field:Email(message = "Email không đúng định dạng!")
    val email: String,

    @field:NotBlank(message = "Mật khẩu không được phép để trống!")
    val password: String
)