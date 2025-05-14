package com.example.aquariumshopapp.ui.screens.product_details

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.datastore.AccountDataStore
import com.example.aquariumshopapp.data.datastore.TokenDataStore
import com.example.aquariumshopapp.data.model.CartItem
import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage
import com.example.aquariumshopapp.data.service.ShoppingCartService
import com.example.aquariumshopapp.ui.utils.NotificationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductDetailsViewModel: ViewModel() {
    private val _product = MutableStateFlow(Product())
    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    private val _images = MutableStateFlow<List<ProductImage>>(emptyList())
    private val _productRelated = MutableStateFlow<List<Product>>(emptyList())
    private val _productImageRelated = MutableStateFlow<List<ProductImage>>(emptyList())

    val product: StateFlow<Product> = _product
    val comments: StateFlow<List<Comment>> = _comments
    val images: StateFlow<List<ProductImage>> = _images
    val productRelated: StateFlow<List<Product>> = _productRelated
    val productImageRelated: StateFlow<List<ProductImage>> = _productImageRelated

    suspend fun getData(id: Long) {
        try {
            val response = RetrofitClient.instance.getDataProductDetails(id)
            if (response.isSuccessful) {
                val message = response.body()?.message
                val data = response.body()?.data

                Log.e("PRODUCT_DETAILS SUCCESSFUL", message.toString())

                _product.value = data?.product!!
                _comments.value = data.comments
                _images.value = data.productImages
                _productRelated.value = data.productRelated
                _productImageRelated.value = data.productImageRelated

                Log.e("PRODUCT_DETAILS LOG", "${product}")
                Log.e("PRODUCT_DETAILS LOG", "${images}")
                Log.e("PRODUCT_DETAILS LOG", "${comments}")
            }else {
                Log.e("PRODUCT_DETAILS FAIL", response.errorBody().toString())
            }
        }catch (e: Exception) {
            Log.e("PRODUCT_DETAILS EX", e.message.toString())
            e.printStackTrace()
        }
    }

    suspend fun addToShoppingCart(context: Context) {
        val accountDataStore = AccountDataStore(context)
        val userId = accountDataStore.getAccount()?.id.toString()

        val isInCart = ShoppingCartService.isProductInCart(userId, product.value.id)

        if (isInCart) {
            NotificationUtils.showNotification(
                context = context,
                message = "Sản phẩm này đã có trong giỏ hàng!"
            )
            Log.e("SHOPPING_CART", "SẢN PHẨM ĐÃ CÓ TRONG GIỎ HÀNG!")
        } else {
            val cartItem = CartItem(
                productId = product.value.id,
                image = images.value.find { it.product?.id == product.value.id }?.name.toString(),
                name = product.value.name.toString(),
                quantity = 1,
                price = product.value.price?.toInt()!!,
                discountPercentage = product.value.discountPercentage!!
            )

            ShoppingCartService.addCartItem(cartItem, userId)

            NotificationUtils.showNotification(
                context = context,
                message = "Đã thêm sản phẩm ${product.value.name} vào giỏ hàng!"
            )
            Log.e("SHOPPING_CART", "THÊM SẢN PHẨM THÀNH CÔNG!")
        }
    }


}