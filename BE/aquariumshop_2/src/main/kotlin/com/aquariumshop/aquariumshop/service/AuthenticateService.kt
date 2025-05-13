package com.aquariumshop.aquariumshop.service

import com.aquariumshop.aquariumshop.dto.request.AccountCreateRequest
import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.request.VerifyEmailRequest
import com.aquariumshop.aquariumshop.dto.request.VerifyPasswordResetRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAuthenticateResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAuthenticateResponse
import com.aquariumshop.aquariumshop.enums.Role
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.ModelAndView

interface AuthenticateService {
    fun adminRegister(request: AccountCreateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>>

//    fun adminLogin(request: AuthenticateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>>

    fun adminPasswordReset(request: AuthenticateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>>

    fun customerRegister(request: AccountCreateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>>

    fun customerLogin(request: AuthenticateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>>

    fun customerPasswordReset(request: AuthenticateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>>

    fun generateRandomName(role: Role): String

    fun handleVerifyPasswordReset(request: VerifyPasswordResetRequest): ModelAndView

    fun handleVerifyEmail(request: VerifyEmailRequest): ModelAndView

    fun generateToken(): String

}