package com.aquariumshop.aquariumshop.dto.response

data class CustomerAuthenticateResponse(
    val isAuthenticated: Boolean?,
    val token: String?,
    val account : CustomerAccountResponse? = null
)
