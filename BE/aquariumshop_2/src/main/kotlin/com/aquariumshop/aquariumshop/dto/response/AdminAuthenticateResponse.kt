package com.aquariumshop.aquariumshop.dto.response

data class AdminAuthenticateResponse(
    val isAuthenticated: Boolean?,
    val token: String?,
    val account : AdminAccountResponse? = null
)
