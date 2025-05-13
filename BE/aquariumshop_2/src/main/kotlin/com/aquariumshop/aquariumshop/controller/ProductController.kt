package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CommentResponse
import com.aquariumshop.aquariumshop.dto.response.ProductDetailsResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.mapper.CommentMapper
import com.aquariumshop.aquariumshop.mapper.ProductImageMapper
import com.aquariumshop.aquariumshop.mapper.ProductMapper
import com.aquariumshop.aquariumshop.repository.CommentRepository
import com.aquariumshop.aquariumshop.repository.ProductImageRepository
import com.aquariumshop.aquariumshop.repository.ProductRepository
import com.aquariumshop.aquariumshop.service.impl.JWTServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ProductController(
    val productRepository: ProductRepository,
    val productImageRepository: ProductImageRepository,
    val commentRepository: CommentRepository,
    val commentMapper: CommentMapper,
    val productMapper: ProductMapper,
    val productImageMapper: ProductImageMapper,
) {
    @GetMapping("/customer/product_details")
    fun getProductDetails(@RequestParam id: Long): ResponseEntity<APIResponse<ProductDetailsResponse>> {
        val comments = commentMapper.toResponseList(commentRepository.findByProductId(id))
        val product = productMapper.toResponse(productRepository.findById(id)
            .orElseThrow { RuntimeException("Không tìm thấy sản phẩm") })
        val productImages = productImageMapper.toResponseList(
            productImageRepository.findByProductId(id)
        )
        val productRelated = productMapper.toResponseList(
            productRepository.findByCategory(product.category?.category!!)
        )
        val productImageRelated = productImageMapper.toResponseList(
            productImageRepository.findAll()
        )

        val response = ProductDetailsResponse(
            product = product,
            comments = comments,
            productImages = productImages,
            productRelated = productRelated,
            productImageRelated = productImageRelated
        )

        println("-----------------------------------------")
        println("PRODUCT DETAILS")
        println("PRODUCT product: ${response.product}")
        println("PRODUCT comments: ${response.comments}")
        println("PRODUCT productImages: ${response.productImages}")
        println("-----------------------------------------")
        return ResponseFactory.success(response, "Tải dữ liệu thành công!")
    }
}