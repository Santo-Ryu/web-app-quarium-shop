package com.example.aquariumshopapp.data.api

import com.example.aquariumshopapp.data.model.Comment
import com.example.aquariumshopapp.data.model.request.AccountCreateRequest
import com.example.aquariumshopapp.data.model.request.AuthenticateRequest
import com.example.aquariumshopapp.data.model.request.PaymentRequest
import com.example.aquariumshopapp.data.model.response.APIResponse
import com.example.aquariumshopapp.data.model.response.CustomerAccountResponse
import com.example.aquariumshopapp.data.model.response.CustomerAuthenticateResponse
import com.example.aquariumshopapp.data.model.response.ProductDetailsResponse
import com.google.firestore.v1.Value
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @GET("api/customer/get_account")
    suspend fun getAccount(@Query("id") id: Long): Response<APIResponse<CustomerAccountResponse>>

    @Multipart
    @POST("api/image/update_image")
    suspend fun updateImage(
        @Query("id") id: Long,
        @Query("type") type: String,
        @Part file: MultipartBody.Part
    ): Response<APIResponse<String>>

    @POST("api/customer/update_info")
    suspend fun updateInfo(
        @Query("id") id: Long,
        @Query("type") type: String,
        @Query("value") value: String
    ): Response<APIResponse<String>>

    @POST("api/customer/payment")
    suspend fun payment(@Body request: PaymentRequest): Response<APIResponse<String>>

    @POST("api/customer/change_password")
    suspend fun changePassword(@Body request: AuthenticateRequest): Response<APIResponse<Any>>

    @POST("api/customer/comment")
    suspend fun comment(@Body request: Comment): Response<APIResponse<Any>>
}