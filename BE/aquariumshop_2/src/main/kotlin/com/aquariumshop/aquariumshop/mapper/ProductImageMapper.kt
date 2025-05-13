package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.ProductImageResponse
import com.aquariumshop.aquariumshop.model.entity.ProductImage
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [ProductImageMapper::class])
interface ProductImageMapper {
    fun toResponse(productImage: ProductImage): ProductImageResponse
    fun toEntity(response: ProductImageResponse): ProductImage
    fun toResponseList(productImage: List<ProductImage>): List<ProductImageResponse>
    fun toEntityList(responses: List<ProductImageResponse>): List<ProductImage>
}