package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAccountResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.service.impl.AdminServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
}