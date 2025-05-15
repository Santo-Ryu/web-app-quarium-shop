package com.aquariumshop.aquariumshop.service

import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAccountResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import org.springframework.http.ResponseEntity

interface AdminService {
    fun getAccount(id: Long): ResponseEntity<APIResponse<AdminAccountResponse>>
    fun updateInfo(id: Long, type: String, value: String): ResponseEntity<APIResponse<String>>
    fun changePassword(request: AuthenticateRequest): ResponseEntity<APIResponse<Any>>
}