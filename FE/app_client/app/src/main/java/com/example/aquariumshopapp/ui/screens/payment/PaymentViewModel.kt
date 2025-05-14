package com.example.aquariumshopapp.ui.screens.payment

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.datastore.AccountDataStore
import com.example.aquariumshopapp.data.enums.PaymentMethod
import com.example.aquariumshopapp.data.model.CartItem
import com.example.aquariumshopapp.data.model.Category
import com.example.aquariumshopapp.data.model.Customer
import com.example.aquariumshopapp.data.model.Order
import com.example.aquariumshopapp.data.model.OrderItem
import com.example.aquariumshopapp.data.model.ProductImage
import com.example.aquariumshopapp.data.model.request.PaymentRequest
import com.example.aquariumshopapp.data.service.ShoppingCartService
import com.example.aquariumshopapp.ui.utils.NotificationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaymentViewModel: ViewModel() {
    private val _customer = MutableStateFlow(Customer())
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    private val _productImages = MutableStateFlow<List<ProductImage>>(emptyList())
    private val _isDataLoaded = MutableStateFlow(false)
    private val _carts = MutableStateFlow<List<CartItem>>(emptyList())
    private val _total = MutableStateFlow(0)

    val customer: StateFlow<Customer> = _customer
    val orders: StateFlow<List<Order>> = _orders
    val orderItems: StateFlow<List<OrderItem>> = _orderItems
    val categories: StateFlow<List<Category>> = _categories
    val productImages: StateFlow<List<ProductImage>> = _productImages
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded
    val carts: StateFlow<List<CartItem>> = _carts
    val total: StateFlow<Int> = _total

    suspend fun getAccount(context: Context) {
        try {
            val accountDataStore = AccountDataStore(context)
            val id = accountDataStore.getAccount()?.id
            val response = RetrofitClient.instance.getAccount(id!!)

            if (response.isSuccessful) {
                val data = response.body()?.data
                accountDataStore.saveAccount(data?.customer!!)
                _customer.value = data.customer
                _orders.value = data.orders
                _orderItems.value = data.orderItems
                _categories.value = data.categories
                _productImages.value = data.productImages
                _isDataLoaded.value = true // đánh dấu đã tải xong

                ShoppingCartService.getCartAndTotal(
                    userId = id.toString(),
                    onSuccess = { items, total ->
                        _carts.value = items
                        carts.value.forEach {
                            Log.d("Firestore", "Sản phẩm: ${it.name} - SL: ${it.quantity}")
                        }

                        _total.value = total
                    },
                    onFailure = { error ->
                        Log.e("Firestore", "Lỗi khi lấy giỏ hàng: ${error.message}")
                    }
                )
                Log.e("GET_ACCOUNT", customer.value.toString())
            }
        }catch (e: Exception) {
            e.printStackTrace()
            Log.e("GET_ACCOUNT", e.message.toString())
        }
    }

    suspend fun payment(context: Context, paymentMethod: String, note: String, navController: NavController) {
        try {
            if (paymentMethod == PaymentMethod.QR.toString()) {
                return NotificationUtils.showNotification(context, "Ứng dụng chưa cập nhật tính năng này!")
            }
            val request = PaymentRequest(
                id = customer.value.id,
                carts = carts.value,
                paymentMethod = paymentMethod,
                note = note
            )

            val response = RetrofitClient.instance.payment(request)
            if (response.isSuccessful) {
                navController.navigate("home")
                ShoppingCartService.clearCart(customer.value.id.toString())
                NotificationUtils.showNotification(context, "Đặt hàng thành công!")
            }else {
                NotificationUtils.showNotification(context, "Đặt hàng thất bại!")
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}