package com.example.aquariumshopapp.data.api

import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.request.AccountCreateRequest
import com.example.aquariumshopapp.data.model.request.AuthenticateRequest
import com.example.aquariumshopapp.data.model.response.APIResponse
import com.example.aquariumshopapp.data.model.response.CustomerAccountResponse
import com.example.aquariumshopapp.data.model.response.CustomerAuthenticateResponse
import com.example.aquariumshopapp.data.model.response.ProductDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("api/auth/customer/register")
    suspend fun register(@Body request: AccountCreateRequest): Response<APIResponse<CustomerAuthenticateResponse>>

    @POST("api/auth/customer/login")
    suspend fun login(@Body request: AuthenticateRequest): Response<APIResponse<CustomerAuthenticateResponse>>

    @POST("api/auth/customer/password_reset")
    suspend fun forgot(@Body request: AuthenticateRequest): Response<APIResponse<CustomerAuthenticateResponse>>

    @GET("api/public/get_home_data")
    suspend fun getDataHome(): Response<APIResponse<CustomerAccountResponse>>

    @GET("api/customer/product_details")
    suspend fun getDataProductDetails(@Query("id") id: Long): Response<APIResponse<ProductDetailsResponse>>

    @GET("api/customer/get_all_comment")
    suspend fun getCommentsByProductId(@Query("id") id: Long): Response<APIResponse<List<Comment>>>
}