package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.ProductResponse
import com.aquariumshop.aquariumshop.model.entity.Product
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [CategoryMapper::class, ProductImageMapper::class])
interface ProductMapper {
    fun toResponse(product: Product): ProductResponse
    fun toEntity(response: ProductResponse): Product
    fun toResponseList(products: List<Product>): List<ProductResponse>
    fun toEntityList(responses: List<ProductResponse>): List<Product>
}