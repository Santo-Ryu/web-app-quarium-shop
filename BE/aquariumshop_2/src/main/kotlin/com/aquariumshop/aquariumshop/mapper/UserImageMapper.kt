package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.UserImageResponse
import com.aquariumshop.aquariumshop.model.entity.UserImage
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserImageMapper {
    fun toResponse(userImage: UserImage): UserImageResponse
    fun toEntity(response: UserImageResponse): UserImage
    fun toResponseList(userImages: List<UserImage>): List<UserImageResponse>
    fun toEntityList(responses: List<UserImageResponse>): List<UserImage>
}