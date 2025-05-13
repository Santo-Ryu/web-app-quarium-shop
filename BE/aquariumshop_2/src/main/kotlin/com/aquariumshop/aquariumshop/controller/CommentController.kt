package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CommentResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.mapper.CommentMapper
import com.aquariumshop.aquariumshop.repository.CommentRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class CommentController(
    val commentRepository: CommentRepository,
    val commentMapper: CommentMapper
) {
    @GetMapping("/customer/get_all_comment")
    fun getCommentsByProductId(@RequestParam id: Long): ResponseEntity<APIResponse<List<CommentResponse>>> {

        println("-----------------------------")
        println("GET_COMMENT_BY_PRODUCT_ID")
        val comments = commentRepository.findByProductId(id)
        val response = commentMapper.toResponseList(comments)
        println("-----------------------------")
        return ResponseFactory.success(response, "Tải dữ liệu thành công!")
    }
}