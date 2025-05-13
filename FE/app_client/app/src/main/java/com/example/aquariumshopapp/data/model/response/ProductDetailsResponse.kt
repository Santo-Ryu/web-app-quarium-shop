package com.example.aquariumshopapp.data.model.response

import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage

data class ProductDetailsResponse(
    val product: Product?,
    val comments: List<Comment> = emptyList(),
    val productImages: List<ProductImage> = emptyList(),
    val productRelated: List<Product> = emptyList(),
    val productImageRelated: List<ProductImage> = emptyList(),
)
