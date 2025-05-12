package com.example.aquariumshopapp.data.service

import com.example.aquariumshopapp.data.model.ProductImage

class FilterImageList {
    companion object {
        fun filterImagesByProductId(images: List<ProductImage>, id: Long): List<ProductImage> {
            return images.filter { it.product.id == id }
        }
    }
}