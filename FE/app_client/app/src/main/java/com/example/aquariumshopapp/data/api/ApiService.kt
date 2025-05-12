package com.example.aquariumshopapp.data.api

import com.example.aquariumshopapp.data.model.request.AccountCreateRequest
import com.example.aquariumshopapp.data.model.request.AuthenticateRequest
import com.example.aquariumshopapp.data.model.response.APIResponse
import com.example.aquariumshopapp.data.model.response.CustomerAccountResponse
import com.example.aquariumshopapp.data.model.response.CustomerAuthenticateResponse
import okhttp3.ResponseBody
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

    @GET("api/public/image")
    suspend fun getImage(@Query("name") name: String): Response<ResponseBody>
}