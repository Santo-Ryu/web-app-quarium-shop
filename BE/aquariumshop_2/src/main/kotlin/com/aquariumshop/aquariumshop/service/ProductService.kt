package com.aquariumshop.aquariumshop.service

import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.ProductResponse
import org.springframework.http.ResponseEntity

interface ProductService {
    fun getHomeData(): ResponseEntity<APIResponse<CustomerAccountResponse>>
}