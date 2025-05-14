package com.example.aquariumshopapp.ui.screens.components

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.aquariumshopapp.data.model.CartItem
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.service.ShoppingCartService
import com.example.aquariumshopapp.ui.utils.NotificationUtils

class ProductCardViewModel: ViewModel() {
    suspend fun addToShoppingCart(product: Product, userId: String, context: Context, image: String): Unit {
        val isInCart = ShoppingCartService.isProductInCart(userId, product.id)

        if (isInCart) {
            NotificationUtils.showNotification(
                context = context,
                message = "Sản phẩm này đã có trong giỏ hàng!"
            )
            Log.e("SHOPPING_CART", "SẢN PHẨM ĐÃ CÓ TRONG GIỎ HÀNG!")
        } else {
            val cartItem = CartItem(
                productId = product.id,
                image = image,
                name = product.name.toString(),
                quantity = 1,
                price = product.price?.toInt()!!,
                discountPercentage = product.discountPercentage!!
            )

            ShoppingCartService.addCartItem(cartItem, userId)

            NotificationUtils.showNotification(
                context = context,
                message = "Đã thêm sản phẩm ${product.name} vào giỏ hàng!"
            )
            Log.e("SHOPPING_CART", "THÊM SẢN PHẨM THÀNH CÔNG!")
        }
    }
}