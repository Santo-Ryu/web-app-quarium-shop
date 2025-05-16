package com.aquariumshop.aquariumshop.service

import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.request.OrderUpdateRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAccountResponse
import com.aquariumshop.aquariumshop.dto.response.AdminResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerResponse
import org.springframework.http.ResponseEntity

interface AdminService {
    fun getAccount(id: Long): ResponseEntity<APIResponse<AdminAccountResponse>>
    fun updateInfo(id: Long, type: String, value: String): ResponseEntity<APIResponse<String>>
    fun changePassword(request: AuthenticateRequest): ResponseEntity<APIResponse<Any>>
    fun destroyOrder(id: Long): ResponseEntity<APIResponse<Any>>
    fun deleteProductInOrder(productId: Long, orderId: Long): ResponseEntity<APIResponse<Any>>
    fun updateOrder(request: OrderUpdateRequest): ResponseEntity<APIResponse<Any>>
    fun deleteCustomer(id: Long): ResponseEntity<APIResponse<Any>>
    fun updateCustomer(request: CustomerResponse): ResponseEntity<APIResponse<Any>>
    fun updateAdmin(request: AdminResponse): ResponseEntity<APIResponse<Any>>
}