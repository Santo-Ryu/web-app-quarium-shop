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
    val customerAuthenticateService: AuthenticateService
) {
    @PostMapping("/admin/register")
    fun adminRegister(@RequestBody @Valid request: AccountCreateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>> {
        return customerAuthenticateService.adminRegister(request)
    }

//    @PostMapping("/admin/login")
//    fun adminLogin(@RequestBody @Valid request: AuthenticateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>> {
//        return customerAuthenticateService.adminLogin(request)
//    }

    @PostMapping("/admin/password_reset")
    fun adminPasswordReset(@RequestBody @Valid request: AuthenticateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>> {
        return customerAuthenticateService.adminPasswordReset(request)
    }

    @PostMapping("/customer/register")
    fun customerRegister(@RequestBody @Valid request: AccountCreateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>> {
        return customerAuthenticateService.customerRegister(request)
    }

    @PostMapping("/customer/login")
    fun customerLogin(@RequestBody @Valid request: AuthenticateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>> {
        return customerAuthenticateService.customerLogin(request)
    }

    @PostMapping("/customer/password_reset")
    fun customerPasswordReset(@RequestBody @Valid request: AuthenticateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>> {
        return customerAuthenticateService.customerPasswordReset(request)
    }

    @GetMapping("/customer/verify_password_reset")
    fun customerVerifyPasswordReset(
        @RequestParam token: String,
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam role: String
    ): ModelAndView {
        return customerAuthenticateService.handleVerifyPasswordReset(
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
        return customerAuthenticateService.handleVerifyEmail(
            request = VerifyEmailRequest(token = token, email = email, role = role)
        )
    }
}