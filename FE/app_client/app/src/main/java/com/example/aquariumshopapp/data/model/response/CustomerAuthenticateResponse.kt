package com.example.aquariumshopapp.data.model.response

data class CustomerAuthenticateResponse(
    val isAuthenticated: Boolean,
    val token: String?,
    val account : CustomerAccountResponse? = null
)
