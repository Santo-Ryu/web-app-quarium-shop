package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.request.DiscountProductRequest
import com.aquariumshop.aquariumshop.dto.request.ProductUpdateRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CommentResponse
import com.aquariumshop.aquariumshop.dto.response.ProductDetailsResponse
import com.aquariumshop.aquariumshop.dto.response.ProductResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.mapper.CommentMapper
import com.aquariumshop.aquariumshop.mapper.ProductImageMapper
import com.aquariumshop.aquariumshop.mapper.ProductMapper
import com.aquariumshop.aquariumshop.model.entity.Category
import com.aquariumshop.aquariumshop.model.entity.Product
import com.aquariumshop.aquariumshop.model.entity.ProductImage
import com.aquariumshop.aquariumshop.repository.CategoryRepository
import com.aquariumshop.aquariumshop.repository.CommentRepository
import com.aquariumshop.aquariumshop.repository.ProductImageRepository
import com.aquariumshop.aquariumshop.repository.ProductRepository
import com.aquariumshop.aquariumshop.service.impl.ImageServiceImpl
import com.aquariumshop.aquariumshop.service.impl.JWTServiceImpl
import com.aquariumshop.aquariumshop.service.impl.ProductServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class ProductController(
    val productRepository: ProductRepository,
    val productImageRepository: ProductImageRepository,
    val commentRepository: CommentRepository,
    val commentMapper: CommentMapper,
    val productMapper: ProductMapper,
    val productImageMapper: ProductImageMapper,
    val productServiceImpl: ProductServiceImpl
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

    @PostMapping("/admin/add_new_product")
    fun addNewProduct(
        @RequestParam category: String,
        @RequestParam name: String,
        @RequestParam description: String,
        @RequestParam price: Int,
        @RequestParam quantity: Int,
        @RequestParam("productImages") productImages: List<MultipartFile>
    ): ResponseEntity<APIResponse<Any>> {
        return productServiceImpl.addNewProduct(category, name, description, price, quantity, productImages)
    }

    @PostMapping("/admin/delete_product")
    fun deleteProduct(@RequestParam id: Long): ResponseEntity<APIResponse<Any>> {
        return productServiceImpl.deleteProduct(id)
    }

    @PostMapping("/admin/delete_product_image")
    fun deleteProductImage(@RequestParam id: Long): ResponseEntity<APIResponse<Any>> {
        val productImage = productImageRepository.findById(id).orElseThrow { RuntimeException("Lỗi") }
        productImageRepository.delete(productImage)
        return ResponseFactory.success("", "Xóa ảnh thành công!")
    }

    @PostMapping("/admin/update_product")
    fun updateProduct(@RequestBody request: ProductUpdateRequest): ResponseEntity<APIResponse<Any>> {
        return productServiceImpl.updateProduct(request)
    }

    @PostMapping("/admin/apply_discount")
    fun applyDiscount(@RequestBody request: List<DiscountProductRequest>): ResponseEntity<APIResponse<Any>> {
        return productServiceImpl.applyDiscountProduct(request)
    }

    @PostMapping("/admin/destroy_discount")
    fun destroyDiscount(@RequestBody request: List<DiscountProductRequest>): ResponseEntity<APIResponse<Any>> {
        return productServiceImpl.destroyDiscountProduct(request)
    }

    @PostMapping("/customer/comment")
    fun comment(@RequestBody request: CommentResponse): ResponseEntity<APIResponse<Any>> {
        return productServiceImpl.comment(request)
    }

}