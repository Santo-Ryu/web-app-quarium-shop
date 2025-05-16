package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.dto.request.DiscountProductRequest
import com.aquariumshop.aquariumshop.dto.request.ProductUpdateRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CommentResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.mapper.CategoryMapper
import com.aquariumshop.aquariumshop.mapper.ProductImageMapper
import com.aquariumshop.aquariumshop.mapper.ProductMapper
import com.aquariumshop.aquariumshop.model.entity.Comment
import com.aquariumshop.aquariumshop.model.entity.Product
import com.aquariumshop.aquariumshop.repository.CategoryRepository
import com.aquariumshop.aquariumshop.repository.CommentRepository
import com.aquariumshop.aquariumshop.repository.CustomerRepository
import com.aquariumshop.aquariumshop.repository.ProductImageRepository
import com.aquariumshop.aquariumshop.repository.ProductRepository
import com.aquariumshop.aquariumshop.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import kotlin.collections.forEach

@Service
class ProductServiceImpl(
    val productRepository: ProductRepository,
    val categoryRepository: CategoryRepository,
    val productImageRepository: ProductImageRepository,
    val productMapper: ProductMapper,
    val categoryMapper: CategoryMapper,
    val productImageMapper: ProductImageMapper,
    val imageServiceImpl: ImageServiceImpl,
    val customerRepository: CustomerRepository,
    val commentRepository: CommentRepository
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

    override fun comment(request: CommentResponse): ResponseEntity<APIResponse<Any>> {
        val product = productRepository.findById(request.product.id).orElseThrow{ RuntimeException("Lỗi") }
        val customer = customerRepository.findById(request.customer.id).orElseThrow{ RuntimeException("Lỗi") }

        val comment = Comment()
        comment.product = product
        comment.customer = customer
        comment.rating = request.rating
        comment.content = request.content
        commentRepository.save(comment)

        val commentOfProduct = commentRepository.findByProductId(request.id)
        if (commentOfProduct.isNotEmpty()) {
            var ratingSum = 0
            commentOfProduct.forEach { item ->
                ratingSum += item.rating!!
            }
            product.rating = ratingSum.toFloat() / commentOfProduct.size
            productRepository.save(product)
        }

        return ResponseFactory.success("", "Bình luận thành công!")
    }

    override fun addNewProduct(
        category: String,
        name: String,
        description: String,
        price: Int,
        quantity: Int,
        productImages: List<MultipartFile>
    ): ResponseEntity<APIResponse<Any>> {
        if (category.isEmpty() || name.isEmpty() || description.isEmpty() || price == 0 || quantity == 0 || productImages.isEmpty())
            return ResponseFactory.badRequest("Chưa nhập đầy đủ thông tin!")
        if (price < 0)
            return ResponseFactory.badRequest("Giá sản phẩm phải > 0")
        if (quantity < 0)
            return ResponseFactory.badRequest("Số lượng sản phẩm phải > 0")

        val productExists = productRepository.findByName(name)
        if (productExists != null) {
            return ResponseFactory.badRequest("Sản phẩm đã tồn tại!")
        }

        val categoryOfProduct= categoryRepository.findByCategory(category)

        val newProduct = Product()
        newProduct.name = name
        newProduct.category = categoryOfProduct
        newProduct.description = description
        newProduct.price = price
        newProduct.quantity = quantity
        productRepository.save(newProduct)

        val product = productRepository.findByName(name)
        productImages.forEach { item ->
            imageServiceImpl.updateImage(product?.id!!, "product", item)
        }

        return ResponseFactory.success("ok", "Thểm sản phẩm thành công!")
    }

    override fun deleteProduct(id: Long): ResponseEntity<APIResponse<Any>> {
        val product = productRepository.findById(id).orElseThrow { RuntimeException("Lỗi") }
        productImageRepository.deleteByProductId(product.id!!)
        productRepository.delete(product)
        return ResponseFactory.success("ok", "Xóa thành công!")
    }

    override fun updateProduct(request: ProductUpdateRequest): ResponseEntity<APIResponse<Any>> {
        val product = productRepository.findById(request.id).orElseThrow { RuntimeException("Lỗi") }

        val categoryOfProduct = categoryRepository.findByCategory(request.category?.category!!)
        product.category = categoryOfProduct
        product.name = request.name
        product.description = request.description
        product.quantity = product.quantity
        product.price = product.price
        productRepository.save(product)

        println("--------------------")
        println("UPDATE-PRODUCT")
        println("--------------------")

        return ResponseFactory.success("", "Cập nhật thành công!")
    }

    override fun applyDiscountProduct(request: List<DiscountProductRequest>): ResponseEntity<APIResponse<Any>> {
        println("---------------------")
        println("APPLY_DISCOUNT")
        request.forEach { item ->
            println(item)
            val product = productRepository.findById(item.id).orElseThrow { RuntimeException("Lỗi") }
            product.discountPercentage = item.discountPercentage
            product.discountStartDate = LocalDateTime.now()

            println("Date: " + item.discountEndDate)
            println("Local date time: " + dateToLocalDateTime(item.discountEndDate))

            product.discountEndDate = dateToLocalDateTime(item.discountEndDate)
            productRepository.save(product)
        }
        println("---------------------")
        return ResponseFactory.success("", "Áp dụng giảm giá thành công!")
    }

    override fun destroyDiscountProduct(request: List<DiscountProductRequest>): ResponseEntity<APIResponse<Any>> {
        println("---------------------")
        println("DESTROY_DISCOUNT")
        request.forEach { item ->
            println(item)
            val product = productRepository.findById(item.id).orElseThrow { RuntimeException("Lỗi") }
            product.discountPercentage = 0
            product.discountStartDate = null
            product.discountEndDate = null
            productRepository.save(product)
        }
        println("---------------------")
        return ResponseFactory.success("", "Hủy giảm giá thành công!")
    }

    fun dateToLocalDateTime(date: Date?): LocalDateTime {
        return date?.toInstant()
            ?.atZone(ZoneId.systemDefault())
            ?.toLocalDateTime() ?: LocalDateTime.now()
    }

}