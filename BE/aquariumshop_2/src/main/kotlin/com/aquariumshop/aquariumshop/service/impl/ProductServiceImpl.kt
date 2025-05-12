package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.mapper.CategoryMapper
import com.aquariumshop.aquariumshop.mapper.ProductImageMapper
import com.aquariumshop.aquariumshop.mapper.ProductMapper
import com.aquariumshop.aquariumshop.repository.CategoryRepository
import com.aquariumshop.aquariumshop.repository.ProductImageRepository
import com.aquariumshop.aquariumshop.repository.ProductRepository
import com.aquariumshop.aquariumshop.repository.UserImageRepository
import com.aquariumshop.aquariumshop.service.support.ProductService
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaTypeFactory.getMediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.nio.file.Paths

@Service
class ProductServiceImpl(
    val productRepository: ProductRepository,
    val categoryRepository: CategoryRepository,
    val productImageRepository: ProductImageRepository,
    val productMapper: ProductMapper,
    val categoryMapper: CategoryMapper,
    val productImageMapper: ProductImageMapper
): ProductService {
    override fun getHomeData(): ResponseEntity<APIResponse<CustomerAccountResponse>> {
        val products = productMapper.toResponseList(productRepository.findAll())
        val categories = categoryMapper.toResponseList(categoryRepository.findAll())
        val productImages = productImageMapper.toResponseList(productImageRepository.findAll())

        val response = CustomerAccountResponse(
            products = products,
            categories = categories,
            productImages = productImages
        )

        println("GET HOME DATA")

        return ResponseFactory.success(response, "Tải dữ liệu thành công!")
    }
}