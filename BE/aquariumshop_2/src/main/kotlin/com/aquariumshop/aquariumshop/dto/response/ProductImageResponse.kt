package com.aquariumshop.aquariumshop.dto.response

import com.aquariumshop.aquariumshop.model.entity.Product

data class ProductImageResponse(
    val id: Long,
    val name: String,
    val product: ProductResponse
)
