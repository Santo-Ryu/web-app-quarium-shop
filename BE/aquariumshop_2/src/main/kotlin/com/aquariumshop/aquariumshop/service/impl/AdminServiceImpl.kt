package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAccountResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.mapper.AdminMapper
import com.aquariumshop.aquariumshop.mapper.CategoryMapper
import com.aquariumshop.aquariumshop.mapper.CustomerMapper
import com.aquariumshop.aquariumshop.mapper.OrderItemMapper
import com.aquariumshop.aquariumshop.mapper.OrderMapper
import com.aquariumshop.aquariumshop.mapper.ProductImageMapper
import com.aquariumshop.aquariumshop.mapper.ProductMapper
import com.aquariumshop.aquariumshop.model.entity.OrderItem
import com.aquariumshop.aquariumshop.model.entity.ProductImage
import com.aquariumshop.aquariumshop.repository.AdminRepository
import com.aquariumshop.aquariumshop.repository.CategoryRepository
import com.aquariumshop.aquariumshop.repository.CustomerRepository
import com.aquariumshop.aquariumshop.repository.OrderItemRepository
import com.aquariumshop.aquariumshop.repository.OrderRepository
import com.aquariumshop.aquariumshop.repository.ProductImageRepository
import com.aquariumshop.aquariumshop.repository.ProductRepository
import com.aquariumshop.aquariumshop.service.AdminService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import kotlin.collections.forEach

@Service
class AdminServiceImpl(
    val adminMapper: AdminMapper,
    val adminRepository: AdminRepository,
    val productRepository: ProductRepository,
    val productMapper: ProductMapper,
    val productImageRepository: ProductImageRepository,
    val productImageMapper: ProductImageMapper,
    val categoryRepository: CategoryRepository,
    val categoryMapper: CategoryMapper,
    val orderRepository: OrderRepository,
    val orderMapper: OrderMapper,
    val orderItemRepository: OrderItemRepository,
    val orderItemMapper: OrderItemMapper,
    val customerRepository: CustomerRepository,
    val customerMapper: CustomerMapper
): AdminService {
    override fun getAccount(id: Long): ResponseEntity<APIResponse<AdminAccountResponse>> {
        val admin = adminRepository.findById(id).orElseThrow { RuntimeException("Không tìm thấy khách hàng!") }
        val orders = orderRepository.findAll()
        val products = productRepository.findAll()
        val productImages = productImageRepository.findAll()
        val categories = categoryRepository.findAll()
        val orderItems = orderItemRepository.findAll()
        val customers = customerRepository.findAll()

        val response = AdminAccountResponse(
            admin = adminMapper.toResponse(admin),
            orders = orderMapper.toResponseList(orders),
            orderItems = orderItemMapper.toResponseList(orderItems),
            products = productMapper.toResponseList(products),
            productImages = productImageMapper.toResponseList(productImages),
            categories = categoryMapper.toResponseList(categories),
            customers = customerMapper.toResponseList(customers)
        )

        println("------------------------------")
        println("GET_ACCOUNT")
        println("------------------------------")

        return ResponseFactory.success(response, "Tải dữ liệu thành công!")
    }

    override fun updateInfo(
        id: Long,
        type: String,
        value: String
    ): ResponseEntity<APIResponse<String>> {
        TODO("Not yet implemented")
    }

    override fun changePassword(request: AuthenticateRequest): ResponseEntity<APIResponse<Any>> {
        TODO("Not yet implemented")
    }
}