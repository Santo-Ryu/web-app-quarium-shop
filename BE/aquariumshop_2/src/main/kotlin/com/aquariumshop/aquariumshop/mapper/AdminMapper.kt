package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.AdminResponse
import com.aquariumshop.aquariumshop.model.entity.Admin
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [UserImageMapper::class])
interface AdminMapper {
    fun toResponse(admin: Admin): AdminResponse
    fun toEntity(response: AdminResponse): Admin
    fun toResponseList(admin: List<Admin>): List<AdminResponse>
    fun toEntityList(responses: List<AdminResponse>): List<Admin>
}