package com.example.aquariumshopapp.data.model.response

import com.example.aquariumshopapp.data.model.Category
import com.example.aquariumshopapp.data.model.Customer
import com.example.aquariumshopapp.data.model.Order
import com.example.aquariumshopapp.data.model.OrderItem
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage

data class CustomerAccountResponse (
    val customer: Customer? = null,
    val orders: List<Order> = emptyList(),
    val orderItems: List<OrderItem> = emptyList(),
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val productImages: List<ProductImage> = emptyList()
)