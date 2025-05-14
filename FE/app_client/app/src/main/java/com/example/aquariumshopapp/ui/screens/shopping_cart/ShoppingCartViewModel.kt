package com.example.aquariumshopapp.ui.screens.shopping_cart

import android.content.Context
import android.util.Log
import androidx.compose.animation.scaleOut
import androidx.lifecycle.ViewModel
import com.example.aquariumshopapp.data.datastore.AccountDataStore
import com.example.aquariumshopapp.data.model.CartItem
import com.example.aquariumshopapp.data.service.ShoppingCartService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.internal.userAgent

class ShoppingCartViewModel: ViewModel() {
    private val _carts = MutableStateFlow<List<CartItem>>(emptyList())
    val carts: StateFlow<List<CartItem>> = _carts

    suspend fun getCarts(context: Context) {
        val accountDataStore = AccountDataStore(context)
        val id = accountDataStore.getAccount()?.id

        if (id != null) {
            ShoppingCartService.getCart(
                userId = id.toString(),
                onSuccess = { items ->
                    _carts.value = items
                    carts.value.forEach {
                        Log.d("Firestore", "Sản phẩm: ${it.name} - SL: ${it.quantity}")
                    }
                },
                onFailure = { error ->
                    Log.e("Firestore", "Lỗi khi lấy giỏ hàng: ${error.message}")
                }
            )
        } else {
            Log.e("Firestore", "Không có ID người dùng!")
        }
    }

    suspend fun addQuantity(context: Context, cartItem: CartItem) {
        val accountDataStore = AccountDataStore(context)
        val id = accountDataStore.getAccount()?.id.toString()
        val updateCartItem = CartItem(
            productId = cartItem.productId,
            image = cartItem.image,
            name = cartItem.name,
            quantity = 1,
            price = cartItem.price,
            discountPercentage = cartItem.discountPercentage
        )
        ShoppingCartService.addCartItem(
            cartItem = updateCartItem,
            userId = id
        )
        getCarts(context)
        Log.e("SHOPPING_CART", "INCREASE ${carts.value}")
    }

    suspend fun decreaseQuantity(context: Context, cartItem: CartItem) {
        val accountDataStore = AccountDataStore(context)
        val id = accountDataStore.getAccount()?.id.toString()
        val productId = cartItem.productId
        ShoppingCartService.decreaseCartItemQuantity(
            userId = id,
            productId = productId,
            context = context
        )
        getCarts(context)
        Log.e("SHOPPING_CART", "DECREASE ${carts.value}")
    }
}