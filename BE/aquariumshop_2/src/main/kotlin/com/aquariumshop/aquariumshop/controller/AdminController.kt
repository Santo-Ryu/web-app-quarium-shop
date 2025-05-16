package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.request.OrderUpdateRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAccountResponse
import com.aquariumshop.aquariumshop.dto.response.AdminResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerResponse
import com.aquariumshop.aquariumshop.service.impl.AdminServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AdminController(
    val adminServiceImpl: AdminServiceImpl
) {
    @GetMapping("/admin/get_account")
    fun getAccount(@RequestParam id: Long): ResponseEntity<APIResponse<AdminAccountResponse>> {
        return adminServiceImpl.getAccount(id)
    }

    @PostMapping("/admin/destroy_order")
    fun destroyOrder(@RequestParam id: Long): ResponseEntity<APIResponse<Any>> {
        return adminServiceImpl.destroyOrder(id)
    }

    @PostMapping("/admin/delete_product_in_order")
    fun deleteProductInOrder(@RequestParam orderId: Long, @RequestParam productId: Long): ResponseEntity<APIResponse<Any>> {
        return adminServiceImpl.deleteProductInOrder(orderId, productId)
    }

    @PostMapping("/admin/update_order")
    fun updateOrder(@RequestBody request: OrderUpdateRequest): ResponseEntity<APIResponse<Any>> {
        return adminServiceImpl.updateOrder(request)
    }

    @PostMapping("/admin/delete_customer")
    fun deleteCustomer(@RequestParam id: Long): ResponseEntity<APIResponse<Any>> {
        return adminServiceImpl.deleteCustomer(id)
    }

    @PostMapping("/admin/update_customer")
    fun updateCustomer(@RequestBody request: CustomerResponse): ResponseEntity<APIResponse<Any>> {
        return adminServiceImpl.updateCustomer(request)
    }
    @PostMapping("/admin/update_admin")
    fun updateAdmin(@RequestBody request: AdminResponse): ResponseEntity<APIResponse<Any>> {
        return adminServiceImpl.updateAdmin(request)
    }

    @PostMapping("/admin/change_password")
    fun changePassword(@RequestBody request: AuthenticateRequest): ResponseEntity<APIResponse<Any>> {
        return adminServiceImpl.changePassword(request)
    }
}