package com.aquariumshop.aquariumshop.dto.response

data class ProductDetailsResponse (
    val product: ProductResponse? = null,
    val comments: List<CommentResponse> = emptyList(),
    val productImages: List<ProductImageResponse> = emptyList(),
    val productRelated: List<ProductResponse> = emptyList(),
    val productImageRelated: List<ProductImageResponse> = emptyList(),
)