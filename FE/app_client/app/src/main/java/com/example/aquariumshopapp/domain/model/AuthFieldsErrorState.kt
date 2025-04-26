package com.example.aquariumshopapp.domain.model

data class AuthFieldsErrorState(
    val emailError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = ""
)