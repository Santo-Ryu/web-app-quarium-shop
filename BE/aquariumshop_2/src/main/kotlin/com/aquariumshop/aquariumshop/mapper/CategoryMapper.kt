package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.CategoryResponse
import com.aquariumshop.aquariumshop.model.entity.Category
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CategoryMapper {
    fun toResponse(category: Category): CategoryResponse
    fun toEntity(response: CategoryResponse): Category
    fun toResponseList(categories: List<Category>): List<CategoryResponse>
    fun toEntityList(responses: List<CategoryResponse>): List<Category>
}