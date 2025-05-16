package com.example.aquariumshopapp.ui.screens.personal

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.datastore.AccountDataStore
import com.example.aquariumshopapp.data.datastore.TokenDataStore
import com.example.aquariumshopapp.data.enums.Role
import com.example.aquariumshopapp.data.model.Category
import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.Customer
import com.example.aquariumshopapp.data.model.Order
import com.example.aquariumshopapp.data.model.OrderItem
import com.example.aquariumshopapp.data.model.ProductImage
import com.example.aquariumshopapp.data.model.request.AuthenticateRequest
import com.example.aquariumshopapp.data.service.EncryptService
import com.example.aquariumshopapp.ui.utils.NotificationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileNotFoundException

class PersonalViewModel: ViewModel() {
    private val _customer = MutableStateFlow(Customer())
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    private val _productImages = MutableStateFlow<List<ProductImage>>(emptyList())
    private val _isDataLoaded = MutableStateFlow(false)
    private val _comments = MutableStateFlow<List<Comment>>(emptyList())

    val customer: StateFlow<Customer> = _customer
    val orders: StateFlow<List<Order>> = _orders
    val orderItems: StateFlow<List<OrderItem>> = _orderItems
    val categories: StateFlow<List<Category>> = _categories
    val productImages: StateFlow<List<ProductImage>> = _productImages
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded

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
                _comments.value = data.comments
                _isDataLoaded.value = true // đánh dấu đã tải xong
                Log.e("GET_ACCOUNT", customer.value.toString())
            }
        }catch (e: Exception) {
            e.printStackTrace()
            Log.e("GET_ACCOUNT", e.message.toString())
        }
    }

    suspend fun updateInfo(
        type: String,
        text: String,
        gender: String,
        birthDate: String,
        context: Context,
        navController: NavController
    ) {
        var value = ""
        if (type in listOf("name", "phone", "address")) {
            if (!text.isEmpty()) {
                value = text
            }else return NotificationUtils.Companion.showNotification(context, "Không được để trống thông tin!")
        }
        if (type.equals("gender")) value = gender
        if (type.equals("birthDate")) {
            if (!birthDate.isEmpty()) {
                value = birthDate
            }else {
                Log.e("UPDATE_INFO", birthDate)
                return NotificationUtils.Companion.showNotification(context, "Không được để trống thông tin!")
            }
        }
        val accountDataStore = AccountDataStore(context)
        val id = accountDataStore.getAccount()?.id

        try {
            val response = RetrofitClient.instance.updateInfo(
                id = id!!,
                type = EncryptService.Companion.encrypt(type),
                value = EncryptService.Companion.encrypt(value)
            )
            if (response.isSuccessful) {
                NotificationUtils.Companion.showNotification(
                    context = context,
                    message = "Cập nhật thông tin thành công!"
                )
                getAccount(context)
                NotificationUtils.Companion.showNotification(context, "Cập nhật thông tin thành công!")
                navController.navigate("personal_info")
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateImage(uri: Uri, context: Context) {
        val accountDataStore = AccountDataStore(context)
        val id = accountDataStore.getAccount()?.id

        // Lấy file từ URI
        val file = uri.toFile(context)

        // Tạo RequestBody từ file
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        // Tạo MultipartBody.Part từ RequestBody
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)


        try {
            val response = RetrofitClient.instance.updateImage(id!!, "customer", filePart)
            if (response.isSuccessful) {
                NotificationUtils.Companion.showNotification(
                    context = context,
                    message = "Cập nhật hình ảnh thành công!"
                )
                getAccount(context)
            }else {
                NotificationUtils.Companion.showNotification(
                    context = context,
                    message = "Cập nhật hình ảnh không thành công!"
                )
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun Uri.toFile(context: Context): File {
        val cursor = context.contentResolver.query(this, null, null, null, null)
        cursor?.moveToFirst()
        val index = cursor?.getColumnIndex(MediaStore.Images.Media.DATA) ?: -1
        val filePath = cursor?.getString(index) ?: throw FileNotFoundException("File not found")
        cursor?.close()
        return File(filePath)
    }

    suspend fun logout(context: Context, navController: NavController) {
        val tokenDataStore = TokenDataStore(context)
        tokenDataStore.clearToken()
        navController.navigate("auth")
    }

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String,
        navController: NavController,
        context: Context
    ) {
       try {
           if (currentPassword.length < 6 || newPassword.length < 6 || confirmPassword.length < 6) {
               NotificationUtils.showNotification(context, "Mật khẩu có độ dài phải lớn hơn 6 ký tự!")
           }
           if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
               NotificationUtils.showNotification(context, "Không được để trống mật khẩu!")
           }
           if (currentPassword.equals(newPassword) && currentPassword.equals(confirmPassword)) {
               NotificationUtils.showNotification(context, "Mật khẩu mới phải khác mật khẩu cũ!")
           }
           if (!newPassword.equals(confirmPassword)) {
               NotificationUtils.showNotification(context, "Mật khẩu mới không trùng khớp!")
           }

           val request = AuthenticateRequest(
               email = EncryptService.encrypt(customer.value.email!!),
               password = EncryptService.hashPassword(newPassword),
               role = Role.CUSTOMER.toString()
           )
           val response = RetrofitClient.instance.changePassword(request)
           if (response.isSuccessful) {
               navController.navigate("auth")
               NotificationUtils.showNotification(context, "Đổi mật khẩu thành công!")
           }else {
               navController.navigate("personal")
               NotificationUtils.showNotification(context, "Đổi mật khẩu thất bại!")
           }

       }catch (e: Exception) {
           e.printStackTrace()
       }
    }
}