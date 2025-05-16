package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.request.OrderUpdateRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAccountResponse
import com.aquariumshop.aquariumshop.dto.response.AdminResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.enums.Gender
import com.aquariumshop.aquariumshop.mapper.AdminMapper
import com.aquariumshop.aquariumshop.mapper.CategoryMapper
import com.aquariumshop.aquariumshop.mapper.CustomerMapper
import com.aquariumshop.aquariumshop.mapper.OrderItemMapper
import com.aquariumshop.aquariumshop.mapper.OrderMapper
import com.aquariumshop.aquariumshop.mapper.ProductImageMapper
import com.aquariumshop.aquariumshop.mapper.ProductMapper
import com.aquariumshop.aquariumshop.model.entity.OrderItem
import com.aquariumshop.aquariumshop.model.entity.OrderStatus
import com.aquariumshop.aquariumshop.model.entity.ProductImage
import com.aquariumshop.aquariumshop.repository.AdminRepository
import com.aquariumshop.aquariumshop.repository.CategoryRepository
import com.aquariumshop.aquariumshop.repository.CustomerRepository
import com.aquariumshop.aquariumshop.repository.OrderItemRepository
import com.aquariumshop.aquariumshop.repository.OrderRepository
import com.aquariumshop.aquariumshop.repository.OrderStatusRepository
import com.aquariumshop.aquariumshop.repository.ProductImageRepository
import com.aquariumshop.aquariumshop.repository.ProductRepository
import com.aquariumshop.aquariumshop.repository.UserImageRepository
import com.aquariumshop.aquariumshop.service.AdminService
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
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
    val customerMapper: CustomerMapper,
    val orderStatusRepository: OrderStatusRepository,
    val userImageRepository: UserImageRepository,
    val encryptServiceImpl: EncryptServiceImpl,
    val notificationService: NotificationService
): AdminService {
    override fun getAccount(id: Long): ResponseEntity<APIResponse<AdminAccountResponse>> {
        val admin = adminRepository.findById(id).orElseThrow { RuntimeException("Không tìm thấy admin!") }
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
        productImages.forEach { item -> println(item.toString()) }
        println("------------------------------")

        return ResponseFactory.success(response, "Tải dữ liệu thành công!")
    }

    override fun updateCustomer(request: CustomerResponse): ResponseEntity<APIResponse<Any>> {
        val customer = customerRepository.findById(request.id).orElseThrow { RuntimeException("Lỗi") }
        customer.name = request.name
        customer.gender = request.gender!!
        customer.phone = request.phone
        customer.address = request.address
        customer.birthDate = stringToDate(request.birthDate.toString())
        customerRepository.save(customer)

        println("------BIRTH_DATE_LOG------")
        println(stringToDate(request.birthDate.toString()))
        println(request.birthDate)
        println(customer.birthDate)
        val b = customerRepository.findById(customer.id).orElseThrow { RuntimeException("") }
        println(b.birthDate)
        println("--------------------------")

        return ResponseFactory.success(customer, "Tải dữ liệu thành công!")
    }

    override fun updateAdmin(request: AdminResponse): ResponseEntity<APIResponse<Any>> {
        val admin = adminRepository.findById(request.id).orElseThrow { RuntimeException("Lỗi") }
        admin.name = request.name
        admin.gender = request.gender!!
        admin.phone = request.phone
        admin.birthDate = stringToDate(request.birthDate.toString())
        adminRepository.save(admin)

        println("------BIRTH_DATE_LOG------")
        println(stringToDate(request.birthDate.toString()))
        println(request.birthDate)
        println(admin.birthDate)
        val b = customerRepository.findById(admin.id).orElseThrow { RuntimeException("") }
        println(b.birthDate)
        println("--------------------------")

        return ResponseFactory.success(admin, "Tải dữ liệu thành công!")
    }

    override fun destroyOrder(id: Long): ResponseEntity<APIResponse<Any>> {
        val order = orderRepository.findById(id).orElseThrow { RuntimeException("Không tìm thấy đơn hàng!") }
        orderItemRepository.deleteByOrderId(order.id!!)
        orderRepository.delete(order)
        return ResponseFactory.success("", "Hủy đơn hàng thành công!")
    }

    override fun deleteProductInOrder(orderId: Long, productId: Long): ResponseEntity<APIResponse<Any>> {
        val row = orderItemRepository.deleteByProductIdAndOrderId(orderId, productId)

        if (row > 0) {
            val order = orderRepository.findById(orderId).orElseThrow { RuntimeException("Không tìm thấy đơn hàng!") }
            val remainingItems = orderItemRepository.findByOrderId(orderId)
            if (remainingItems.size == 0 ) {
                orderRepository.delete(order)
            }else {
                var subtotal = 0
                remainingItems.forEach { item ->
                    subtotal += item.subtotal!!
                }
                order.price = subtotal
                orderRepository.save(order)
            }
            return ResponseFactory.success("", "Xóa sản phẩm thành công!")
        }
        return ResponseFactory.badRequest("Xóa sản phẩm thất bại!")
    }

    override fun updateInfo(
        id: Long,
        type: String,
        value: String
    ): ResponseEntity<APIResponse<String>> {
        TODO("Not yet implemented")
    }

    override fun changePassword(request: AuthenticateRequest): ResponseEntity<APIResponse<Any>> {
        try {
            val email = encryptServiceImpl.decrypt(request.email)
            val admin = adminRepository.findByEmail(email)!!
            admin.password = request.password
            adminRepository.save(admin)

            return ResponseFactory.success("ok", "Đổi mật khẩu thành công!")
        }catch (e: Exception) {
            e.printStackTrace()
            return ResponseFactory.success("ok", "Đổi mật khẩu thất bại!")
        }
    }

    override fun deleteCustomer(id: Long): ResponseEntity<APIResponse<Any>> {
        val customer = customerRepository.findById(id).orElseThrow { RuntimeException("Lỗi") }
        customerRepository.delete(customer)
        customer.image?.id?.let { userImageRepository.deleteById(it) }
        return ResponseFactory.success("OK", "Xóa thành công!")
    }

    override fun updateOrder(request: OrderUpdateRequest): ResponseEntity<APIResponse<Any>> {
        val orderId = request.orderId
        val status = request.orderStatus
        val listItem = request.products

        var price = 0
        listItem.forEach { e ->
            val discountedPrice = e.price * (1 - e.discount.toDouble() / 100)
            price += (discountedPrice * e.quantity).toInt()
        }

        val statusE = orderStatusRepository.findByStatusName(status)
        val order = orderRepository.findById(orderId).orElseThrow{RuntimeException("Lỗi")}

        if (order.status?.statusName != statusE.statusName) {
            println("CREATE_NOTIFICATIONS")
            notificationService.createNotificationToFirestore(
                userId = order.customer?.id.toString(),
                title = "Đơn hàng #${order.id} của bạn ${statusE.statusName?.lowercase()}!",
                orderId = order.id.toString(),
                status = statusE.statusName.toString()
            )
        }

        order.status = statusE
        order.price = price
        orderRepository.save(order)

        val orderItems = orderItemRepository.findByOrderId(orderId)
        orderItems.forEach { e ->
            val p = listItem.find { it.productId == e.product?.id }
            val price = p?.price ?: 0
            val discountPercent = p?.discount?.toDouble() ?: 0.0
            val quantity = p?.quantity ?: 0

            val discountedPrice = (price * (1 - discountPercent / 100)).toInt()

            e.quantity = quantity
            e.subtotal = discountedPrice
            orderItemRepository.save(e)
        }
        return ResponseFactory.success("ok", "Cập nhật thành công!")
    }

    fun stringToDate(dateString: String, pattern: String = "yyyy-MM-dd"): Date? {
        return try {
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            formatter.parse(dateString)
        } catch (e: Exception) {
            null // Trả về null nếu sai định dạng
        }
    }
}