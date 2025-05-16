package com.example.aquariumshopapp.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    const val BASE_URL = "http://192.168.101.26:8080/aquarium_shop/"

    private var token: String? = null

    // Cài đặt token từ bên ngoài
    fun setToken(newToken: String) {
        token = newToken
    }

    private val authInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()

        // Nếu có token, thêm vào header Authorization
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        chain.proceed(requestBuilder.build())
    }
    private fun createRetrofitClient(withAuth: Boolean): ApiService {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (withAuth) {
            builder.addInterceptor(authInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val publicInstance: ApiService by lazy { createRetrofitClient(false) }
    val instance: ApiService by lazy { createRetrofitClient(true) }
    val authInstance: ApiService by lazy { createRetrofitClient(false) }
}