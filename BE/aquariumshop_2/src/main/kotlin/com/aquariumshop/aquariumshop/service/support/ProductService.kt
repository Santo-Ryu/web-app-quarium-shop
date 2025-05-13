package com.aquariumshop.aquariumshop.service.support

import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

interface ProductService {
    fun getHomeData(): ResponseEntity<APIResponse<CustomerAccountResponse>>
}