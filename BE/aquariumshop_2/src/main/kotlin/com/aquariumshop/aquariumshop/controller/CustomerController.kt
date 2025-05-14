package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.request.PaymentRequest
import com.aquariumshop.aquariumshop.dto.request.UpdateImageRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.service.impl.CustomerServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class CustomerController(
    val customerServiceImpl: CustomerServiceImpl
) {
    @GetMapping("/customer/get_account")
    fun getAccount(@RequestParam id: Long): ResponseEntity<APIResponse<CustomerAccountResponse>> {
        return customerServiceImpl.getAccount(id)
    }

    @PostMapping("/customer/update_info")
    fun updateInfo(
        @RequestParam id: Long,
        @RequestParam type: String,
        @RequestParam value: String
    ): ResponseEntity<APIResponse<String>> {
        return customerServiceImpl.updateInfo(id, type, value)
    }

    @PostMapping("/customer/payment")
    fun payment(@RequestBody request: PaymentRequest): ResponseEntity<APIResponse<String>> {
        return customerServiceImpl.payment(request)
    }

    @PostMapping("/customer/change_password")
    fun changePassword(@RequestBody request: AuthenticateRequest): ResponseEntity<APIResponse<Any>> {
        return customerServiceImpl.changePassword(request)
    }
}