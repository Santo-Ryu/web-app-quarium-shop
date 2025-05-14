package com.aquariumshop.aquariumshop.service

import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.request.PaymentRequest
import com.aquariumshop.aquariumshop.dto.request.UpdateImageRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import org.springframework.http.ResponseEntity


interface CustomerService {
    fun getAccount(id: Long): ResponseEntity<APIResponse<CustomerAccountResponse>>
    fun updateInfo(id: Long, type: String, value: String): ResponseEntity<APIResponse<String>>
    fun payment(request: PaymentRequest): ResponseEntity<APIResponse<String>>
    fun changePassword(request: AuthenticateRequest): ResponseEntity<APIResponse<Any>>
}