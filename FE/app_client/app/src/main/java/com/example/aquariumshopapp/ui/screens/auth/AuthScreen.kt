package com.example.aquarium_app.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aquarium_app.ui.screens.auth.components.AuthForm
import com.example.aquariumshopapp.ui.model.AuthUIState
import com.example.aquariumshopapp.ui.screens.auth.AuthViewModel

@Composable
fun AuthScreen(viewModel: AuthViewModel = viewModel()) {
    val authFieldsState by viewModel.authFieldsState.collectAsState()
    val authFieldsErrorState by viewModel.authFieldsErrorState.collectAsState()
    val currentForm by viewModel.currentForm.collectAsState()

    /*  Component  */
    AuthForm(
        AuthUIState(
            authFieldsState,
            authFieldsErrorState,
            currentForm
        ),
        { formType -> viewModel.onSwitchForm(formType) },
        { updatedState -> viewModel.onInputChanged(updatedState) },
        { viewModel.onRegisterClicked() },
    )
}