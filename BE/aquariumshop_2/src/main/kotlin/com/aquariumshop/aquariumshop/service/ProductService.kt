package com.aquariumshop.aquariumshop.service

import com.aquariumshop.aquariumshop.dto.request.DiscountProductRequest
import com.aquariumshop.aquariumshop.dto.request.ProductUpdateRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.ProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface ProductService {
    fun getHomeData(): ResponseEntity<APIResponse<CustomerAccountResponse>>
    fun addNewProduct(category: String, name: String, description: String, price: Int, quantity: Int, productImages: List<MultipartFile>): ResponseEntity<APIResponse<Any>>
    fun deleteProduct(id: Long): ResponseEntity<APIResponse<Any>>
    fun updateProduct(request: ProductUpdateRequest): ResponseEntity<APIResponse<Any>>
    fun applyDiscountProduct(request: List<DiscountProductRequest>): ResponseEntity<APIResponse<Any>>
    fun destroyDiscountProduct(request: List<DiscountProductRequest>): ResponseEntity<APIResponse<Any>>
}