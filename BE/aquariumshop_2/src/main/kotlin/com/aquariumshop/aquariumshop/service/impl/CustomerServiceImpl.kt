package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.request.PaymentRequest
import com.aquariumshop.aquariumshop.dto.request.UpdateImageRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.enums.Gender
import com.aquariumshop.aquariumshop.enums.PaymentMethod
import com.aquariumshop.aquariumshop.mapper.CategoryMapper
import com.aquariumshop.aquariumshop.mapper.CustomerMapper
import com.aquariumshop.aquariumshop.mapper.OrderItemMapper
import com.aquariumshop.aquariumshop.mapper.OrderMapper
import com.aquariumshop.aquariumshop.mapper.ProductImageMapper
import com.aquariumshop.aquariumshop.model.entity.Order
import com.aquariumshop.aquariumshop.model.entity.OrderItem
import com.aquariumshop.aquariumshop.model.entity.OrderStatus
import com.aquariumshop.aquariumshop.model.entity.ProductImage
import com.aquariumshop.aquariumshop.repository.CategoryRepository
import com.aquariumshop.aquariumshop.repository.CustomerRepository
import com.aquariumshop.aquariumshop.repository.OrderItemRepository
import com.aquariumshop.aquariumshop.repository.OrderRepository
import com.aquariumshop.aquariumshop.repository.OrderStatusRepository
import com.aquariumshop.aquariumshop.repository.ProductImageRepository
import com.aquariumshop.aquariumshop.repository.ProductRepository
import com.aquariumshop.aquariumshop.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

@Service
class CustomerServiceImpl(
    val customerRepository: CustomerRepository,
    val orderRepository: OrderRepository,
    val orderItemRepository: OrderItemRepository,
    val categoryRepository: CategoryRepository,
    val productImageRepository: ProductImageRepository,
    val productRepository: ProductRepository,
    val orderStatusRepository: OrderStatusRepository,

    val customerMapper: CustomerMapper,
    val orderMapper: OrderMapper,
    val orderItemMapper: OrderItemMapper,
    val categoryMapper: CategoryMapper,
    val productImageMapper: ProductImageMapper,
    val encryptServiceImpl: EncryptServiceImpl
): CustomerService {
    override fun getAccount(id: Long): ResponseEntity<APIResponse<CustomerAccountResponse>> {
        val customer = customerRepository.findById(id).orElseThrow { RuntimeException("Không tìm thấy khách hàng!") }
        val orders = orderRepository.findByCustomerId(customer.id!!)

        val orderItems: MutableList<OrderItem> = mutableListOf()
        orders.forEach { order ->
            orderItems.addAll(orderItemRepository.findByOrderId(order.id!!))
        }

        val categories = categoryRepository.findAll()

        val productImages: MutableList<ProductImage> = mutableListOf()
        orderItems.forEach { item ->
            productImages.addAll(productImageRepository.findByProductId(item.product?.id!!))
        }

        val response = CustomerAccountResponse(
            customer = customerMapper.toResponse(customer),
            orders = orderMapper.toResponseList(orders),
            orderItems = orderItemMapper.toResponseList(orderItems),
            categories = categoryMapper.toResponseList(categories),
            productImages = productImageMapper.toResponseList(productImages)
        )

        println("------------------------------")
        println("GET_ACCOUNT")
        println(response)
        println("------------------------------")

        return ResponseFactory.success(response, "Tải dữ liệu thành công!")
    }

    override fun updateInfo(id: Long, type: String, value: String): ResponseEntity<APIResponse<String>> {
        val _type = encryptServiceImpl.decrypt(type)
        val _value = encryptServiceImpl.decrypt(value)

        val customer = customerRepository.findById(id).orElseThrow { RuntimeException("Không tìm thấy khách hàng!") }
        if (_type.equals("name")) customer.name = _value
        if (_type.equals("phone")) customer.phone = _value
        if (_type.equals("address")) customer.address = _value
        if (_type.equals("gender")) customer.gender = getGender(_value)
        if (_type.equals("birthDate")) customer.birthDate = stringToDate(_value)
        customerRepository.save(customer)
        return ResponseFactory.success("ok", "Cập nhật thông tin thành công!")
    }

    override fun payment(request: PaymentRequest): ResponseEntity<APIResponse<String>> {
        try {
            val carts = request.carts
            val paymentMethod = request.paymentMethod
            val customer = customerRepository.findById(request.id).orElseThrow { RuntimeException("Không tìm thấy khách hàng!") }
            val products = productRepository.findAll()

            if (paymentMethod == PaymentMethod.COD.toString()) {
                var total = 0
                carts.forEach { item ->
                    val product = products.find { it.id == item.productId }!!
                    val price = product.price * (1 - product.discountPercentage.toDouble()/100)
                    total += price.toInt()
                }

                val orderStatus = orderStatusRepository.findByStatusName("Đang xử lý")

                val order = Order()
                order.paymentMethod = PaymentMethod.COD
                order.customer = customer
                order.status = orderStatus
                order.note = request.note
                order.price = total
                order.orderDate = LocalDateTime.now()
                orderRepository.save(order)

                carts.forEach { cart ->
                    val subtotal = cart.price * (1 - cart.discountPercentage.toDouble()/100)

                    val orderItem = OrderItem()
                    orderItem.order = order
                    orderItem.price = products.find { it.id == cart.productId }?.price
                    orderItem.product = products.find { it.id == cart.productId }
                    orderItem.quantity = cart.quantity
                    orderItem.discountPercent = cart.discountPercentage
                    orderItem.subtotal = subtotal.toInt()

                    orderItemRepository.save(orderItem)
                }
                return ResponseFactory.success("ok","Đặt hàng thành công!")
            }else {
                return ResponseFactory.success("ok","Đặt hàng thành công!")
            }
        }catch (e: Exception) {
            e.printStackTrace()
            return ResponseFactory.badRequest("Có lỗi xảy ra!")
        }
    }

    override fun changePassword(request: AuthenticateRequest): ResponseEntity<APIResponse<Any>> {
        try {
            val email = encryptServiceImpl.decrypt(request.email)
            val customer = customerRepository.findByEmail(email)!!
            customer.password = request.password
            customerRepository.save(customer)

            return ResponseFactory.success("ok", "Đổi mật khẩu thành công!")
        }catch (e: Exception) {
            e.printStackTrace()
            return ResponseFactory.success("ok", "Đổi mật khẩu thất bại!")
        }
    }

    fun getGender(gender: String): Gender {
        return when (gender) {
            "Nam" -> Gender.MALE
            "Nữ" -> Gender.FEMALE
            else -> Gender.OTHER
        }
    }

    fun stringToDate(dateString: String, pattern: String = "dd/MM/yyyy"): Date? {
        return try {
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            formatter.parse(dateString)
        } catch (e: Exception) {
            null // Trả về null nếu sai định dạng
        }
    }
}