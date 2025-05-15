package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.request.AccountCreateRequest
import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.request.VerifyEmailRequest
import com.aquariumshop.aquariumshop.dto.request.VerifyPasswordResetRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAuthenticateResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAuthenticateResponse
import com.aquariumshop.aquariumshop.service.AuthenticateService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val authenticateService: AuthenticateService
) {
    @PostMapping("/admin/register")
    fun adminRegister(@RequestBody @Valid request: AccountCreateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>> {
        println("ADMIN_REGISTER")
        return authenticateService.adminRegister(request)
    }

    @PostMapping("/admin/login")
    fun adminLogin(@RequestBody @Valid request: AuthenticateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>> {
        return authenticateService.adminLogin(request)
    }

    @PostMapping("/admin/password_reset")
    fun adminPasswordReset(@RequestBody @Valid request: AuthenticateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>> {
        return authenticateService.adminPasswordReset(request)
    }

    @GetMapping("/admin/verify_password_reset")
    fun adminVerifyPasswordReset(
        @RequestParam token: String,
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam role: String
    ): ModelAndView {
        return authenticateService.handleVerifyPasswordReset(
            request = VerifyPasswordResetRequest(
                email = email,
                password = password,
                token = token,
                role = role
            )
        )
    }

    @GetMapping("/admin/verify_email")
    fun adminVerifyEmail(
        @RequestParam token: String,
        @RequestParam email: String,
        @RequestParam role: String
    ): ModelAndView {
        return authenticateService.handleVerifyEmail(
            request = VerifyEmailRequest(token = token, email = email, role = role)
        )
    }

    @PostMapping("/customer/register")
    fun customerRegister(@RequestBody @Valid request: AccountCreateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>> {
        return authenticateService.customerRegister(request)
    }

    @PostMapping("/customer/login")
    fun customerLogin(@RequestBody @Valid request: AuthenticateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>> {
        return authenticateService.customerLogin(request)
    }

    @PostMapping("/customer/password_reset")
    fun customerPasswordReset(@RequestBody @Valid request: AuthenticateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>> {
        return authenticateService.customerPasswordReset(request)
    }

    @GetMapping("/customer/verify_password_reset")
    fun customerVerifyPasswordReset(
        @RequestParam token: String,
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam role: String
    ): ModelAndView {
        return authenticateService.handleVerifyPasswordReset(
            request = VerifyPasswordResetRequest(
                email = email,
                password = password,
                token = token,
                role = role
            )
        )
    }

    @GetMapping("/customer/verify_email")
    fun customerVerifyEmail(
        @RequestParam token: String,
        @RequestParam email: String,
        @RequestParam role: String
    ): ModelAndView {
        return authenticateService.handleVerifyEmail(
            request = VerifyEmailRequest(token = token, email = email, role = role)
        )
    }
}