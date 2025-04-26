package com.example.aquariumshopapp.ui.model.auth

data class AuthForm(
    val type: String, // "login", "register", "forgot"
    val title: String,
    val label: String,
    val switchAccountPrompt: List<String>,
    val forgotPasswordPrompt: List<String> = emptyList<String>(),
)