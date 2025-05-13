package com.example.aquariumshopapp.ui.screens.product_details

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.datastore.TokenDataStore
import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.Product
import com.example.aquariumshopapp.data.model.ProductImage
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


}