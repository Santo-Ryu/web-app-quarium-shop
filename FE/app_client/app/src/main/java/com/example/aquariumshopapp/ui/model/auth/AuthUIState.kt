package com.example.aquariumshopapp.ui.model.auth

import com.example.aquariumshopapp.domain.model.AuthFieldsErrorState
import com.example.aquariumshopapp.domain.model.AuthFieldsState

data class AuthUIState(
    val fields: AuthFieldsState,
    val errors: AuthFieldsErrorState,
    val forms: AuthForm
)