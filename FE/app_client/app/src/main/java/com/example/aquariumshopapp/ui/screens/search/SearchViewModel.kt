package com.example.aquariumshopapp.ui.screens.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.datastore.AccountDataStore
import com.example.aquariumshopapp.data.model.Category
import com.example.aquariumshopapp.data.model.Customer
import com.example.aquariumshopapp.data.model.Order
import com.example.aquariumshopapp.data.model.OrderItem
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel: ViewModel() {
    private val _customer = MutableStateFlow(Customer())
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    private val _productImages = MutableStateFlow<List<ProductImage>>(emptyList())
    private val _isDataLoaded = MutableStateFlow(false)
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _productImageAll = MutableStateFlow<List<ProductImage>>(emptyList())

    val customer: StateFlow<Customer> = _customer
    val orders: StateFlow<List<Order>> = _orders
    val orderItems: StateFlow<List<OrderItem>> = _orderItems
    val categories: StateFlow<List<Category>> = _categories
    val productImages: StateFlow<List<ProductImage>> = _productImages
    val productImageAll: StateFlow<List<ProductImage>> = _productImageAll
    val isDataLoaded: StateFlow<Boolean> = _isDataLoaded
    val products: StateFlow<List<Product>> = _products

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
                _products.value = data.products
                _productImageAll.value = data.productImageAll

                _isDataLoaded.value = true // đánh dấu đã tải xong
                Log.e("GET_ACCOUNT", productImageAll.value.toString())
            }
        }catch (e: Exception) {
            e.printStackTrace()
            Log.e("GET_ACCOUNT", e.message.toString())
        }
    }

}