package com.example.aquariumshopapp.ui.screens.product_review

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.aquariumshopapp.data.api.RetrofitClient
import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductReviewViewModel: ViewModel() {
    private val _comments = MutableStateFlow<List<Comment>>(emptyList())

    val comments: StateFlow<List<Comment>> = _comments

    suspend fun getAllComment(id: Long) {
        try {
            val response = RetrofitClient.instance.getCommentsByProductId(id)
            if (response.isSuccessful) {
                _comments.value = response.body()?.data!!
                Log.e("COMMENTS_LOG SUCCESSFUL", comments.toString())
            }
            Log.e("COMMENTS_LOG FAIL", response.errorBody().toString())
        }catch (e: Exception) {
            e.printStackTrace()
            Log.e("COMMENTS_LOG EX", e.message.toString())
        }
    }
}