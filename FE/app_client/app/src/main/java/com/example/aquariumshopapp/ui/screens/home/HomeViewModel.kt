package com.example.aquariumshopapp.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.Category
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    private val _productImages = MutableStateFlow<List<ProductImage>>(emptyList())

    val products: StateFlow<List<Product>> = _products
    val categories: StateFlow<List<Category>> = _categories
    val productImages: StateFlow<List<ProductImage>> = _productImages

    fun getHomeData() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.publicInstance.getDataHome()
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    val message = response.body()?.message

                    _products.value = data?.products!!
                    _categories.value = data?.categories!!
                    _productImages.value = data?.productImages!!
                    Log.e("HOME_VM_SUCCESSFUL", response.body()?.message.toString())
                }else {
                    Log.e("HOME_VM_GET_FAIL", response.body()?.message.toString())
                    Log.e("HOME_VM_GET_FAIL", response.errorBody().toString())
                }
            }catch (e: Exception) {
                Log.e("EX_HOME_VM", "${e.message}")
                e.printStackTrace()
            }
        }
    }
}