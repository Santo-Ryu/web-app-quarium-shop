package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.CommentResponse
import com.aquariumshop.aquariumshop.model.entity.Comment
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CommentMapper {
    fun toResponse(comment: Comment): CommentResponse
    fun toEntity(response: CommentResponse): Comment
    fun toResponseList(comment: List<Comment>): List<CommentResponse>
    fun toEntityList(responses: List<CommentResponse>): List<Comment>
}