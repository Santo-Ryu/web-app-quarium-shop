package com.example.aquariumshopapp.data.service

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.example.aquariumshopapp.data.model.CartItem
import com.example.aquariumshopapp.ui.utils.NotificationUtils
import kotlinx.coroutines.tasks.await

class ShoppingCartService {
    companion object {
        suspend fun addCartItem(cartItem: CartItem, userId: String) {
            val db = FirebaseFirestore.getInstance()
            val cartRef = db.collection("carts").document(userId)

            try {
                val document = cartRef.get().await()

                if (document.exists()) {
                    val cartData = document.data
                    val items = (cartData?.get("items") as? MutableList<Map<String, Any>>)?.toMutableList() ?: mutableListOf()

                    val existingItem = items.find { it["productId"] == cartItem.productId }

                    if (existingItem != null) {
                        // Cập nhật số lượng
                        val updatedItems = items.map {
                            if (it["productId"] == cartItem.productId) {
                                val updatedItem = it.toMutableMap()
                                updatedItem["quantity"] = (it["quantity"] as Number).toInt() + cartItem.quantity
                                updatedItem["discountPercentage"] = cartItem.discountPercentage
                                updatedItem
                            } else it
                        }

                        val newTotal = calculateTotal(updatedItems)

                        cartRef.update(mapOf(
                            "items" to updatedItems,
                            "total" to newTotal
                        )).await()

                        Log.d("Firestore", "Sản phẩm đã được cập nhật trong giỏ hàng!")
                    } else {
                        // Thêm sản phẩm mới
                        val newItem = mapOf(
                            "productId" to cartItem.productId,
                            "image" to cartItem.image,
                            "name" to cartItem.name,
                            "quantity" to cartItem.quantity,
                            "price" to cartItem.price,
                            "discountPercentage" to cartItem.discountPercentage
                        )

                        items.add(newItem)
                        val newTotal = calculateTotal(items)

                        cartRef.update(mapOf(
                            "items" to items,
                            "total" to newTotal
                        )).await()

                        Log.d("Firestore", "Sản phẩm mới đã được thêm vào giỏ hàng!")
                    }

                } else {
                    // Tạo mới giỏ hàng
                    val newCartData = mapOf(
                        "items" to listOf(
                            mapOf(
                                "productId" to cartItem.productId,
                                "image" to cartItem.image,
                                "name" to cartItem.name,
                                "quantity" to cartItem.quantity,
                                "price" to cartItem.price,
                                "discountPercentage" to cartItem.discountPercentage
                            )
                        ),
                        "total" to cartItem.price * cartItem.quantity * (1 - cartItem.discountPercentage / 100.0)
                    )

                    cartRef.set(newCartData).await()

                    Log.d("Firestore", "Giỏ hàng mới đã được tạo và sản phẩm đã được thêm!")
                }
            } catch (e: Exception) {
                Log.e("Firestore", "Lỗi khi thêm/cập nhật sản phẩm trong giỏ hàng: $e")
                throw e
            }
        }

        suspend fun isProductInCart(userId: String, productId: Long): Boolean {
            return try {
                val db = FirebaseFirestore.getInstance()
                val document = db.collection("carts").document(userId).get().await()

                if (document.exists()) {
                    val items = document.data?.get("items") as? List<Map<String, Any>> ?: emptyList()
                    items.any { it["productId"] == productId }
                } else {
                    false
                }
            } catch (e: Exception) {
                Log.e("Firestore", "Lỗi kiểm tra sản phẩm trong giỏ hàng: $e")
                false
            }
        }

        fun getCart(userId: String, onSuccess: (List<CartItem>) -> Unit, onFailure: (Exception) -> Unit) {
            val db = FirebaseFirestore.getInstance()
            val cartRef = db.collection("carts").document(userId)

            cartRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val data = document.data
                        val itemsData = data?.get("items") as? List<Map<String, Any>> ?: emptyList()

                        val cartItems = itemsData.mapNotNull { item ->
                            try {
                                CartItem(
                                    productId = (item["productId"] as Number).toLong(),
                                    image = item["image"] as String,
                                    name = item["name"] as String,
                                    quantity = (item["quantity"] as Number).toInt(),
                                    price = (item["price"] as Number).toInt(),
                                    discountPercentage = (item["discountPercentage"] as Number).toInt()
                                )
                            } catch (e: Exception) {
                                null // Bỏ qua item lỗi
                            }
                        }

                        onSuccess(cartItems)
                    } else {
                        onSuccess(emptyList())
                    }
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                }
        }

        fun removeCartItem(userId: String, productId: String) {
            val db = FirebaseFirestore.getInstance()

            val cartRef = db.collection("carts").document(userId)

            cartRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val cartData = document.data
                        val items = cartData?.get("items") as? List<Map<String, Any>>

                        // Lọc bỏ item cần xóa
                        val updatedItems = items?.filterNot { it["productId"] == productId }

                        // Cập nhật lại giỏ hàng với các mục còn lại
                        cartRef.update("items", updatedItems)
                            .addOnSuccessListener {
                                Log.d("Firestore", "Mục đã được xóa khỏi giỏ hàng!")
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Lỗi khi xóa mục trong giỏ hàng: $e")
                            }
                    } else {
                        Log.d("Firestore", "Giỏ hàng không tồn tại!")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Lỗi khi lấy giỏ hàng: $e")
                }
        }

        suspend fun decreaseCartItemQuantity(userId: String, productId: Long, context: Context) {
            val db = FirebaseFirestore.getInstance()
            val cartRef = db.collection("carts").document(userId)

            try {
                val document = cartRef.get().await()

                if (document.exists()) {
                    val cartData = document.data
                    val items = (cartData?.get("items") as? MutableList<Map<String, Any>>)?.toMutableList() ?: mutableListOf()

                    val itemIndex = items.indexOfFirst { it["productId"] == productId }

                    if (itemIndex != -1) {
                        val currentItem = items[itemIndex]
                        val currentQuantity = (currentItem["quantity"] as Number).toInt()

                        if (currentQuantity > 1) {
                            // Giảm số lượng
                            val updatedItem = currentItem.toMutableMap()
                            updatedItem["quantity"] = currentQuantity - 1
                            items[itemIndex] = updatedItem
                        } else {
                            // Xóa sản phẩm
                            items.removeAt(itemIndex)

                            // Gọi thông báo sau khi thao tác thành công (tốt hơn là sau .await())
                            NotificationUtils.showNotification(
                                context = context,
                                message = "Đã xóa 1 sản phẩm khỏi giỏ hàng!"
                            )
                        }

                        val newTotal = calculateTotal(items)

                        cartRef.update(mapOf(
                            "items" to items,
                            "total" to newTotal
                        )).await()

                        Log.d("Firestore", "Đã cập nhật/xóa sản phẩm khỏi giỏ hàng.")
                    } else {
                        Log.d("Firestore", "Không tìm thấy sản phẩm trong giỏ hàng.")
                    }
                } else {
                    Log.d("Firestore", "Giỏ hàng không tồn tại.")
                }
            } catch (e: Exception) {
                Log.e("Firestore", "Lỗi khi cập nhật/xóa sản phẩm trong giỏ hàng: $e")
                throw e
            }
        }

        // Hàm tính tổng tiền từ danh sách sản phẩm
         fun calculateTotal(items: List<Map<String, Any>>): Double {
            var total = 0.0
            for (item in items) {
                val price = (item["price"] as Number).toDouble()
                val quantity = (item["quantity"] as Number).toInt()
                val discount = (item["discountPercentage"] as Number).toDouble()

                total += price * quantity * (1 - discount / 100.0)
            }
            return total
        }
    }
}