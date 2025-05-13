package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAuthenticateResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.service.impl.ImageServiceImpl
import com.aquariumshop.aquariumshop.service.impl.ProductServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PublicController(
    val productServiceImpl: ProductServiceImpl,
    val imageServiceImpl: ImageServiceImpl
) {
    @GetMapping("/public/get_home_data")
    fun getHomeData(): ResponseEntity<APIResponse<CustomerAccountResponse>> {
        return productServiceImpl.getHomeData()
    }

    @GetMapping("/public/image")
    fun getImage(@RequestParam name: String): ResponseEntity<Any> {
        return imageServiceImpl.getImage(name)
    }
}